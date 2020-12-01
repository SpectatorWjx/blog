package com.wang.blog.web.controller.site.auth;

import com.wang.blog.base.lang.Consts;
import com.wang.blog.service.SecurityCodeService;
import com.wang.blog.service.UserService;
import com.wang.blog.vo.UserVO;
import com.wang.blog.web.controller.BaseController;
import com.wang.blog.web.controller.site.Views;
import com.wang.common.common.base.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author wjx
 * @date 2019/08/13
 */
@Controller
public class ForgotController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private SecurityCodeService securityCodeService;

    @GetMapping("/forgot")
    public String view() {
        return view(Views.FORGOT);
    }

    @PostMapping("/forgot")
    @ResponseBody
    public Result reset(String email, String code, String password) {
        try {
            Assert.hasLength(email, "请输入邮箱地址");
            Assert.hasLength(code, "请输入验证码");
            UserVO user = userService.getByEmail(email);
            Assert.notNull(user, "账户不存在");

            securityCodeService.verify(String.valueOf(user.getId()), Consts.CODE_FORGOT, code);
            userService.updatePassword(user.getId(), password);
            return Result.success();
        } catch (Exception e) {
            return Result.failure(e.getMessage());
        }
    }
}
