package com.wang.blog.service.captcha;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/***
 * @classname CaptchaVerifyService
 * @description
 * @author wjx
 * @date 2020/9/23 13:41
 */
public interface CaptchaVerifyService {

    /**
     * 验证码初始化
     * @param request
     * @param response
     * @return
     */
    Object firstRegister(HttpServletRequest request, HttpServletResponse response);

    /**
     * 二次验证
     * @param request
     * @param response
     */
    void secondValidated(HttpServletRequest request, HttpServletResponse response);
}
