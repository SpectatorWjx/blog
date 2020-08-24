package com.wang.blog.base.oauth;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wang.blog.base.oauth.utils.HttpKit;
import com.wang.blog.base.oauth.utils.OpenOauthBean;
import com.wang.blog.base.oauth.utils.TokenUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wjx
 * @date 2019/12/10 11:58
 */
@Data
@Slf4j
public class Oauth {
    private String clientId;
    private String clientSecret;
    private String redirectUri;


    private static final String AUTH_URL = "";
    private static final String TOKEN_URL = "";
    private static final String TOKEN_INFO_URL = "";
    private static final String USER_INFO_URL = "";

    public static Oauth me() {
        return new Oauth();
    }

    protected String getAuthorizeUrl(String authorize, Map<String, String> params) throws java.io.UnsupportedEncodingException {
        return HttpKit.initParams(authorize, params);
    }

    protected String doPost(String url, Map<String, String> params) throws IOException, KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException {
        return HttpKit.post(url, params);
    }

    protected String doPost(String url, Map<String, String> params, Map<String, String> headers) throws IOException, KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException {
        return HttpKit.post(url, params, headers);
    }

    protected String doGet(String url, Map<String, String> params) throws IOException, KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException {
        return HttpKit.get(url, params);
    }

    protected String doGetWithHeaders(String url, Map<String, String> headers) throws IOException, KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException {
        return HttpKit.get(url, null, headers);
    }

    public OpenOauthBean getUserBeanByCode(String code)
            throws Exception {
        return null;
    }
}
