package com.wang.blog.controller.site.auth;

import com.wang.blog.controller.BaseController;
import com.wang.blog.controller.site.Views;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * @author wjx
 * @date 2019/08/13
 */
@Controller
public class LogoutController extends BaseController {

    /**
     * 退出登陆
     *
     * @return
     */
    @RequestMapping("/logout")
    public String logout(HttpServletResponse response) {
        SecurityUtils.getSubject().logout();
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        return Views.REDIRECT_INDEX;
    }

}
