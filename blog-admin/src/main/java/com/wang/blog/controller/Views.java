package com.wang.blog.controller;

/**
 * 页面配置
 *
 * @author wjx
 */
public interface Views {
    /**
     * 登录
     */
    String LOGIN = "/auth/login";

    /**
     * 注册
     */
    String REGISTER = "/auth/register";

    /**
     * 三方登录回调注册
     */
    String OAUTH_REGISTER = "/auth/oauth_register";

    /**
     * 找回密码
     */
    String FORGOT = "/auth/forgot";

    /**
     * 首页
     */
    String INDEX = "/admin/index";

    String REDIRECT_ADMIN = "redirect:/admin";
    String REDIRECT_INDEX = "redirect:/index";
}
