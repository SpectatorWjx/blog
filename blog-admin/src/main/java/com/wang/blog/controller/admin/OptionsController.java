package com.wang.blog.controller.admin;

import com.wang.common.common.base.Result;
import com.wang.blog.vo.OptionVO;
import com.wang.blog.service.OptionsService;
import com.wang.blog.service.PostSearchService;
import com.wang.blog.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 系统配置
 *
 * @author wjx
 *
 */
@Controller
@RequestMapping("/admin/options")
public class OptionsController extends BaseController {
	@Autowired
	private OptionsService optionsService;
	@Autowired
	private PostSearchService postSearchService;

	@RequestMapping("/index")
	public String index(ModelMap model) {
		OptionVO optionVo= optionsService.findOption();

		model.put("options", optionVo);
		return "/admin/options/index";
	}
	
	@RequestMapping("/update")
	public String update(@RequestParam Map<String, String> body, ModelMap model) {
		optionsService.update(body);
		OptionVO optionVo= optionsService.findOption();
		model.put("options", optionVo);
		model.put("vo", Result.success());
		return "redirect:/admin/options/index";
	}

	/**
	 * 刷新系统变量
	 * @return
	 */
	@RequestMapping("/reload_options")
	@ResponseBody
	public Result reloadOptions() {
		optionsService.reloadCache();
		return Result.success();
	}

	@RequestMapping("/reset_indexes")
	@ResponseBody
	public Result resetIndexes() {
		postSearchService.resetIndexes();
		return Result.success();
	}
}
