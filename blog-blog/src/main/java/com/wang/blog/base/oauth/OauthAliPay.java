package com.wang.blog.base.oauth;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.wang.blog.base.oauth.utils.EnumOauthTypeBean;
import com.wang.blog.base.oauth.utils.OathConfig;
import com.wang.blog.base.oauth.utils.OpenOauthBean;
import com.wang.blog.base.oauth.utils.TokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wjx
 * @date 2019/12/10
 */
public class OauthAliPay extends Oauth {
    private static final String AUTH_URL = "https://openauth.alipay.com/oauth2/publicAppAuthorize.htm";

    public static OauthAliPay me() {
        return new OauthAliPay();
    }

    public OauthAliPay() {
        setClientId(OathConfig.getValue("openid_alipay"));
        setClientSecret(OathConfig.getValue("openkey_alipay"));
        setRedirectUri(OathConfig.getValue("redirect_alipay"));
    }

    String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhjtWl3olqaOq2tb15J6HynRNd5jUjtUnJhbFAxF7gJp5Cu++SI0hMM5cH44soUGjunL+W6Iye/2zQo62WM7JsBNdWBVMcDBS+r7GAkpMmaKJd9c+c+7Moinddqm2vXLqxt+YcRF3Dv9toLdPa3xr1OPt8B3pHG7PAlRUNDeU++1mpnoPClC6duNc7E42rAqjwrM2wlcx570h+p8sUfriuqm1yleTYgAUf7OeKS72+OctGWfinnCbrfURmp87XaLZzM+UHiB1l07qvYZCGrfDhWObbPh9Xfk6PjqZ0khuYURjtvrLiaqvk9C7RV7Wk394FkMlBEskP0nOMu/5gh3wswIDAQAB";
    String SERVER_URL = "https://openapi.alipay.com/gateway.do";


    public String getAuthorizeUrl(String state)
            throws UnsupportedEncodingException {
        Map<String, String> params = new HashMap<>();
        params.put("scope", "auth_user");
        params.put("app_id", getClientId());
        params.put("redirect_uri", getRedirectUri());
        if (StringUtils.isNotBlank(state)) {
            params.put("state", state);
        }
        return super.getAuthorizeUrl(AUTH_URL, params);
    }

    public Map<String, String> getTokenByCode(String code){
        AlipayClient alipayClient = new DefaultAlipayClient(SERVER_URL,
                getClientId(), getClientSecret(), "json", "utf-8", ALIPAY_PUBLIC_KEY, "RSA2");
        AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
        request.setCode(code);
        request.setGrantType("authorization_code");
        Map<String, String> tokenMap = new HashMap<>();
        try {
            AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient.execute(request);
            tokenMap.put("accessToken", oauthTokenResponse.getAccessToken());
            tokenMap.put("expiresIn", oauthTokenResponse.getExpiresIn());
            tokenMap.put("refreshToken", oauthTokenResponse.getRefreshToken());
        } catch (AlipayApiException e) {
            //处理异常
            e.printStackTrace();
        }
        return tokenMap;
    }

    public JSONObject getUserInfo(String accessToken) {
        AlipayClient alipayClient = new DefaultAlipayClient(SERVER_URL,
                getClientId(), getClientSecret(), "json", "utf-8", ALIPAY_PUBLIC_KEY, "RSA2");
        AlipayUserInfoShareRequest request = new AlipayUserInfoShareRequest();
        AlipayUserInfoShareResponse response = null;
        try {
            response = alipayClient.execute(request, accessToken);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        JSONObject dataMap = JSON.parseObject(JSON.toJSONString(response));
        return dataMap;
    }

    public JSONObject getUserInfoByCode(String code){
        Map<String, String> tokenMap = getTokenByCode(code);
        String accessToken = tokenMap.get("accessToken");
        if (StringUtils.isBlank(accessToken)) {
            return null;
        }
        JSONObject dataMap = getUserInfo(accessToken);
        dataMap.put("access_token", accessToken);
        return dataMap;
    }

    @Override
    public OpenOauthBean getUserBeanByCode(String code)
            throws Exception {
        OpenOauthBean openOauthBean = null;
        JSONObject userInfo = me().getUserInfoByCode(code);

        if (userInfo != null) {
            openOauthBean = new OpenOauthBean();
            String username = "ALI" + TokenUtil.md5(userInfo.getString("userId"));
            openOauthBean.setAccessToken(userInfo.getString("accessToken"));
            openOauthBean.setExpireIn(userInfo.getString("expiresIn"));
            openOauthBean.setRefreshToken(userInfo.getString("refreshToken"));
            openOauthBean.setOauthUserId(userInfo.getString("userId"));
            openOauthBean.setOauthType(EnumOauthTypeBean.TYPE_ALI_PAY.getValue());
            openOauthBean.setUsername(username);
            openOauthBean.setNickname(userInfo.getString("nickname"));
            openOauthBean.setAvatar(userInfo.getString("avatar"));
        } else {
            throw new Exception();
        }
        return openOauthBean;
    }
}
