package com.wang.blog.web.controller.auth;

import com.wang.common.common.base.Result;
import com.wang.blog.vo.AccountProfile;
import com.wang.blog.web.controller.BaseController;
import com.wang.blog.web.controller.Views;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 登录
 * @author wjx
 */
@Controller
public class LoginController extends BaseController {

    /**
     * 跳转登录页
     * @return
     */
	@GetMapping(value = "/login")
	public String view() {
		return view(Views.LOGIN);
	}

    /**
     * 提交登录
     * @param username
     * @param password
     * @param model
     * @return
     */
	@PostMapping(value = "/login")
	public String login(String username,
                        String password,
                        @RequestParam(value = "rememberMe",defaultValue = "0") Boolean rememberMe,
                        ModelMap model) {
		String view = view(Views.LOGIN);

        Result<AccountProfile> result = executeLogin(username, password, rememberMe);

        if (result.getCode()==200) {
            view = String.format(Views.REDIRECT_ADMIN, result.getData().getId());
        } else {
            model.put("message", result.getMessage());
        }
        return view;
	}

}
