package com.wang.blog.base.oauth.utils;

import com.wang.blog.base.oauth.ApiConfig;


/**
 * @author wjx
 * @date 2019/12/10
 */
public class OathConfig {
    public static String getValue(String key) {
        return ApiConfig.getInstance().getValue(key);
    }
}
