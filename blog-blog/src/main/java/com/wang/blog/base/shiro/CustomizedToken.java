package com.wang.blog.base.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @author wjx
 */
public class CustomizedToken extends UsernamePasswordToken {

    /**
     *登陆类型
     */
    private String loginType;

    public CustomizedToken(final String username, final String password , boolean rememberMe,String loginType) {
        super(username,password,rememberMe);
        this.loginType = loginType;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }
}