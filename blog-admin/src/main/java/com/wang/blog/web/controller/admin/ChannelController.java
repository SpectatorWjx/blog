package com.wang.blog.web.controller.admin;

import com.wang.blog.base.lang.Consts;
import com.wang.common.common.base.Result;
import com.wang.blog.service.ChannelService;
import com.wang.blog.web.controller.BaseController;
import com.wang.common.entity.blog.ChannelEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wjx
 *
 */
@Controller("adminChannelController")
@RequestMapping("/admin/channel")
public class ChannelController extends BaseController {
	@Autowired
	private ChannelService channelService;
	
	@RequestMapping("/list")
	public String list(ModelMap model) {
		model.put("list", channelService.findAll(Consts.IGNORE));
		return "/admin/channel/list";
	}
	
	@RequestMapping("/view")
	public String view(String id, ModelMap model) {
		if (id != null) {
			ChannelEntity view = channelService.getById(id);
			model.put("view", view);
		}
		return "/admin/channel/view";
	}
	
	@RequestMapping("/update")
	public String update(ChannelEntity view) {
		if (view != null) {
			channelService.update(view);
		}
		return "redirect:/admin/channel/list";
	}

	@RequestMapping("/weight")
	@ResponseBody
	public Result weight(@RequestParam String id, HttpServletRequest request) {
		int weight = ServletRequestUtils.getIntParameter(request, "weight", Consts.FEATURED_ACTIVE);
		channelService.updateWeight(id, weight);
		return Result.success();
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public Result delete(String id) {
		Result data = Result.failure("操作失败");
		if (id != null) {
			try {
				channelService.delete(id);
				data = Result.success();

			} catch (Exception e) {
				data = Result.failure(e.getMessage());
			}
		}
		return data;
	}
	
}
