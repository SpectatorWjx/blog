package com.wang.blog.controller;

import com.wang.common.common.base.Result;
import com.wang.blog.base.utils.MD5;
import com.wang.blog.vo.AccountProfile;
import com.wang.blog.base.formatter.StringEscapeEditor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Controller 基类
 *
 * @author wjx
 * @since 3.0
 */
@Slf4j
public class BaseController {

    @InitBinder
    public void initBinder(ServletRequestDataBinder binder) {
        /**
         * 自动转换日期类型的字段格式
         */
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));

        /**
         * 防止XSS攻击
         */
        binder.registerCustomEditor(String.class, new StringEscapeEditor(true, false));
    }

    /**
     * 获取登录信息
     *
     * @return
     */
    protected AccountProfile getProfile() {
        Subject subject = SecurityUtils.getSubject();
        return (AccountProfile) subject.getPrincipal();
    }

    protected PageRequest wrapPageable() {
        return wrapPageable(null);
    }

    protected PageRequest wrapPageable(Sort sort) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        int pageSize = ServletRequestUtils.getIntParameter(request, "pageSize", 10);
        int pageNo = ServletRequestUtils.getIntParameter(request, "pageNo", 1);

        if (null == sort) {
            sort = Sort.unsorted();
        }
        return PageRequest.of(pageNo - 1, pageSize, sort);
    }

    /**
     * 包装分页对象
     *
     * @param pn 页码
     * @param pn 页码
     * @return
     */
    protected PageRequest wrapPageable(Integer pn, Integer pageSize) {
        if (pn == null || pn == 0) {
            pn = 1;
        }
        if (pageSize == null || pageSize == 0) {
            pageSize = 10;
        }
        return PageRequest.of(pn - 1, pageSize);
    }

    protected String view(String view) {
        return view;
    }

    protected Result<AccountProfile> executeLogin(String username, String password, boolean rememberMe) {
        Result<AccountProfile> ret = Result.failure("登录失败");

        if (StringUtils.isAnyBlank(username, password)) {
            return ret;
        }

        UsernamePasswordToken token = new UsernamePasswordToken(username, MD5.md5(password), rememberMe);

        try {
            SecurityUtils.getSubject().login(token);
            ret = Result.success(getProfile());
        } catch (UnknownAccountException e) {
            log.error(e.getMessage());
            ret = Result.failure("用户不存在");
        } catch (LockedAccountException e) {
            log.error(e.getMessage());
            ret = Result.failure("用户被禁用");
        } catch (AuthenticationException e) {
            log.error(e.getMessage());
            ret = Result.failure("用户认证失败");
        }
        return ret;
    }
}
