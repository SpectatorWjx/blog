package com.wang.blog.web.controller;

import com.wang.blog.service.captcha.CaptchaVerifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/***
 * @classname CaptchaVerifyController
 * @description
 * @author wjx
 * @date 2020/9/23 13:21
 */
@Controller
@RequestMapping("captcha")
public class CaptchaVerifyController {

    @Autowired
    CaptchaVerifyService captchaVerifyService;

    @GetMapping("register")
    public void register(HttpServletRequest request, HttpServletResponse response){
        captchaVerifyService.firstRegister(request, response);
    }

    @PostMapping("validate")
    public void validate(HttpServletRequest request, HttpServletResponse response){
        captchaVerifyService.secondValidated(request, response);
    }
}
