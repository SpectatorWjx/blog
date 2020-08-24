package com.wang.blog.web.controller.auth;

import com.wang.common.common.base.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/***
 * @classname: UnAuthController
 * @description:
 * @author: wjx
 * @date: 2020/4/26 13:09
 */
@Controller
public class UnAuthController {
    /**
     * 跳转登录页
     * @return
     */
    @RequestMapping(value = "/reject")
    @ResponseBody
    public Result view() {
          return Result.failure("没有权限操作");
    }
}
