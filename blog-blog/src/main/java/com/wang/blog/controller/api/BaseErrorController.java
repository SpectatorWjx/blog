package com.wang.blog.controller.api;

import com.wang.blog.controller.BaseController;
import com.wang.blog.controller.site.Views;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author wjx
 * @date 2019/08/13
 */
@Controller
@RequestMapping(value = "error")
@Slf4j
public class BaseErrorController extends BaseController implements ErrorController {

    @Override
    public String getErrorPath() {
        return view(Views.NOT_FOUND);
    }

    @RequestMapping
    public String error() {
        return getErrorPath();
    }
}