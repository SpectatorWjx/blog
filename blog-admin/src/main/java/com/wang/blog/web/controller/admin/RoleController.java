/**
 * 
 */package com.wang.blog.web.controller.admin;

import com.wang.common.common.base.Result;
import com.wang.blog.service.PermissionService;
import com.wang.blog.service.RoleService;
import com.wang.blog.web.controller.BaseController;
import com.wang.common.entity.user.RoleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author - wjx on 2018/2/11
 */
@Controller
@RequestMapping("/admin/role")
public class RoleController extends BaseController {
	@Autowired
    private RoleService roleService;
	@Autowired
	private PermissionService permissionService;

	@GetMapping("/list")
	public String paging(String name, ModelMap model) {
		Pageable pageable = wrapPageable();
		Page<RoleEntity> page = roleService.paging(pageable, name);
		model.put("name", name);
		model.put("page", page);
		return "/admin/role/list";
	}

	@RequestMapping("/view")
	public String view(String id, ModelMap model) {
		if (id != null) {
			RoleEntity role = roleService.get(id);
			model.put("view", role);
		}

        model.put("permissions", permissionService.tree());
        return "/admin/role/view";
	}
	
	@RequestMapping("/update")
	public String update(RoleEntity role, @RequestParam(value = "perms", required=false) List<String> perms, ModelMap model) {
		Result data;

		Set<String> permissions = new HashSet<>(perms);
        
        if (RoleEntity.ADMIN_ID.equals( role.getId())) {
			data = Result.failure("管理员角色不可编辑");
        } else {
            roleService.update(role, permissions);
            data = Result.success();
        }
        model.put("vo", data);
        return "redirect:/admin/role/list";
	}
	
	@RequestMapping("/activate")
	@ResponseBody
	public Result activate(String id, Boolean active) {
		Result ret = Result.failure("操作失败");
		if (id != null && !id.equals(RoleEntity.ADMIN_ID)) {
			roleService.activate(id, active);
			ret = Result.success();
		}
		return ret;
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public Result delete(@RequestParam("id") String id) {
		Result ret;
		if (RoleEntity.ADMIN_ID.equals(id)) {
			ret = Result.failure("管理员不能操作");
        }else if(roleService.delete(id)){
        	ret = Result.success();
        }else{
        	ret = Result.failure("该角色不能被操作");
        }
		return ret;
	}
}
