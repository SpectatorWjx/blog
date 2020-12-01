package com.wang.blog.base.oauth;

import lombok.Data;

import java.lang.reflect.Method;

/**
 * @author wjx
 * @date 2019/12/10
 */
@Data
public class ApiConfig {
    private static ApiConfig config = new ApiConfig();

    private String openid_qq;

    private String openkey_qq;

    private String redirect_qq;

    private String openid_sina;

    private String openkey_sina;

    private String redirect_sina;

    private String openid_github;

    private String openkey_github;

    private String redirect_github;

    private String openid_gitee;

    private String openkey_gitee;

    private String redirect_gitee;

    private String openid_alipay;

    private String openkey_alipay;

    private String redirect_alipay;

    private String lbs_ak_baidu;

    private String dp_key;

    private String dp_secret;

    private ApiConfig() {

    }

    public static ApiConfig getInstance() {
        if (config == null) {
            config = new ApiConfig();
        }
        return config;
    }

    public String getValue(String attrName) {
        String firstLetter = attrName.substring(0, 1).toUpperCase();
        String getter = "get" + firstLetter + attrName.substring(1);
        Object value = "";
        try {
            Method method = ApiConfig.class.getMethod(getter, new Class[0]);
            value = method.invoke(config, new Object[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (String) value;
    }
}
