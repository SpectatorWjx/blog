package com.wang.blog.base.oauth.utils;

import com.wang.blog.base.oauth.APIConfig;


/**
 * @author wjx
 * @date 2019/12/10 11:58
 */
public class OathConfig {
    public static String getValue(String key) {
        return APIConfig.getInstance().getValue(key);
    }
}
