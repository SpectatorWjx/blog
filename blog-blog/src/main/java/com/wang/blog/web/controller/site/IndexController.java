package com.wang.blog.web.controller.site;

import com.wang.blog.base.lang.Consts;
import com.wang.blog.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;


/**
 * @author wjx
 * @date 2019/08/13
 */
@Controller
public class IndexController extends BaseController{
	
	@RequestMapping("/index")
	public String root(ModelMap model, HttpServletRequest request) {
		String order = ServletRequestUtils.getStringParameter(request, "order", Consts.order.NEWEST);
		int pageNo = ServletRequestUtils.getIntParameter(request, "pageNo", 1);
		model.put("order", order);
		model.put("pageNo", pageNo);
		return view(Views.INDEX);
	}

	@RequestMapping({"","/"})
	public String index() {
		return view(Views.HOME);
	}

}
