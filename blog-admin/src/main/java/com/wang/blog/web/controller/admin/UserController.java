package com.wang.blog.web.controller.admin;

import com.wang.blog.base.lang.Consts;
import com.wang.common.common.base.Result;
import com.wang.blog.vo.UserVO;
import com.wang.blog.service.RoleService;
import com.wang.blog.service.UserRoleService;
import com.wang.blog.service.UserService;
import com.wang.blog.web.controller.BaseController;
import com.wang.common.entity.user.RoleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author wjx
 *
 */
@Controller("adminUserController")
@RequestMapping("/admin/user")
public class UserController extends BaseController {
	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private UserRoleService userRoleService;

	@RequestMapping("/list")
	public String list(String name, ModelMap model) {
		Pageable pageable = wrapPageable();
		Page<UserVO> page = userService.paging(pageable, name);

		List<UserVO> users = page.getContent();
		List<String> userIds = new ArrayList<>();

		users.forEach(item -> {
			userIds.add(item.getId());
		});

		Map<String, List<RoleEntity>> map = userRoleService.findMapByUserIds(userIds);
		users.forEach(item -> {
			item.setRoles(map.get(item.getId()));
		});

		model.put("name", name);
		model.put("page", page);
		return "/admin/user/list";
	}

	@RequestMapping("/view")
	public String view(String id, ModelMap model) {
		UserVO view = userService.get(id);
		view.setRoles(userRoleService.listRoles(view.getId()));
		model.put("view", view);
		model.put("roles", roleService.list());
		return "/admin/user/view";
	}

	@PostMapping("/update_role")
	public String postAuthc(String id, @RequestParam(value = "roleIds", required=false) Set<String> roleIds, ModelMap model) {
		userRoleService.updateRole(id, roleIds);
		model.put("vo", Result.success());
		return "redirect:/admin/user/list";
	}

	@RequestMapping(value = "/pwd", method = RequestMethod.GET)
	public String pwsView(String id, ModelMap model) {
		UserVO ret = userService.get(id);
		model.put("view", ret);
		return "/admin/user/pwd";
	}

	@RequestMapping(value = "/pwd", method = RequestMethod.POST)
	public String pwd(String id, String newPassword, ModelMap model) {
		UserVO ret = userService.get(id);
		model.put("view", ret);

		try {
			userService.updatePassword(id, newPassword);
			model.put("vo", Result.success());
		} catch (IllegalArgumentException e) {
			model.put("message", e.getMessage());
		}
		return "/admin/user/pwd";
	}

	@RequestMapping("/open")
	@ResponseBody
	public Result open(String id) {
		userService.updateStatus(id, Consts.STATUS_NORMAL);
		return Result.success();
	}

	@RequestMapping("/close")
	@ResponseBody
	public Result close(String id) {
		userService.updateStatus(id, Consts.STATUS_CLOSED);
		return Result.success();
	}
}
