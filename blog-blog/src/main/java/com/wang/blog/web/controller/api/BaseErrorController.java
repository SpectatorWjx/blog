package com.wang.blog.web.controller.api;

import com.wang.blog.web.controller.BaseController;
import com.wang.blog.web.controller.site.Views;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author wjx
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