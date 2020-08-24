package com.wang.blog.web.controller.site.auth;

import com.wang.blog.base.shiro.LoginType;
import com.wang.blog.vo.AccountProfile;
import com.wang.blog.web.controller.BaseController;
import com.wang.blog.web.controller.site.Views;
import com.wang.common.common.base.Result;
import com.wang.common.common.enums.ResultEnum;
import com.wang.common.common.utils.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 登陆
 * @author wjx
 */
@Controller
public class LoginController extends BaseController {

    /**
     * 跳转登陆页
     * @return
     */
	@GetMapping(value = "/login")
	public String view() {
        AccountProfile profile = getProfile();
        if (null == profile || StringUtil.isEmpty(profile.getId())){
            return view(Views.LOGIN);
        }else{
            return String.format(Views.REDIRECT_USER_HOME, profile.getId());
        }
	}

    /**
     * 提交登陆
     * @param username
     * @param password
     * @param model
     * @return
     */
	@PostMapping(value = "/login")
    @ResponseBody
	public Result login(String username, String password, @RequestParam(value = "rememberMe",defaultValue = "0") Boolean rememberMe, ModelMap model) {
        Result<AccountProfile> result = executeLogin(username, password, rememberMe, LoginType.USER.toString());
        if (result.getCode().equals(ResultEnum.SUCCESS.getCode())) {
            return Result.success(String.format(Views.USER_HOME, result.getData().getId()));
        } else {
            return result;
        }
	}

}
