package com.wang.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @author wjx
 *
 */
@Controller
public class IndexController extends BaseController{
	
	@RequestMapping(value= {"/", "/index"})
	public String root() {
		return view(Views.REDIRECT_ADMIN);
	}

}
