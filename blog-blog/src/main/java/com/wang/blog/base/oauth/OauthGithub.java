package com.wang.blog.base.oauth;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
public class OauthGithub extends Oauth {
    private static final Logger LOGGER = LoggerFactory.getLogger(OauthGithub.class);
    private static final String AUTH_URL = "https://github.com/login/oauth/authorize";
    private static final String TOKEN_URL = "https://github.com/login/oauth/access_token";
    private static final String USER_INFO_URL = "https://api.github.com/user";

    public static OauthGithub me() {
        return new OauthGithub();
    }

    public OauthGithub() {
        setClientId(OathConfig.getValue("openid_github"));
        setClientSecret(OathConfig.getValue("openkey_github"));
        setRedirectUri(OathConfig.getValue("redirect_github"));
    }

    public String getAuthorizeUrl(String state)
            throws UnsupportedEncodingException {
        Map<String, String> params = new HashMap<>();
        params.put("response_type", "code");
        params.put("client_id", getClientId());
        params.put("redirect_uri", getRedirectUri());
        if (StringUtils.isNotBlank(state)) {
            params.put("state", state);
        }
        return super.getAuthorizeUrl(AUTH_URL, params);
    }

    public JSONObject getUserInfo(String accessToken)
            throws IOException, KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException {
        Map<String, String> params = new HashMap<>(2);
        params.put("Authorization", "token " + accessToken);
        params.put("User-Agent", "随心小记");
        String userInfo = super.doGetWithHeaders(USER_INFO_URL+"?access_token="+accessToken, params);
        JSONObject dataMap = JSON.parseObject(userInfo);
        LOGGER.debug(dataMap.toJSONString());
        return dataMap;
    }

    public JSONObject getUserInfoByCode(String code)
            throws IOException, KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException {
        Map<String, String> params = new HashMap<>();
        params.put("code", code);
        params.put("client_id", getClientId());
        params.put("client_secret", getClientSecret());
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        Map<String, String> tokenMap = TokenUtil.getAuthToken(super.doPost(TOKEN_URL, params, headers));
        String accessToken = tokenMap.get("accessToken");
        if (StringUtils.isBlank(accessToken)) {
            return null;
        }
        JSONObject dataMap = getUserInfo(accessToken);
        dataMap.put("access_token", accessToken);
        LOGGER.debug(JSON.toJSONString(dataMap));
        return dataMap;
    }

    @Override
    public OpenOauthBean getUserBeanByCode(String code)
            throws Exception {
        OpenOauthBean openOauthBean = null;
        JSONObject userInfo = me().getUserInfoByCode(code);

        if (userInfo != null) {
            openOauthBean = new OpenOauthBean();
            String username = "GITHUB" + TokenUtil.md5(userInfo.getString("node_id"));
            openOauthBean.setAccessToken(userInfo.getString("accessToken"));
            openOauthBean.setExpireIn(userInfo.getString("expiresIn"));
            openOauthBean.setRefreshToken(userInfo.getString("refreshToken"));
            openOauthBean.setOauthUserId(userInfo.getString("node_id"));
            openOauthBean.setOauthType(EnumOauthTypeBean.TYPE_GITHUB.getValue());
            openOauthBean.setUsername(username);
            openOauthBean.setNickname(userInfo.getString("name"));
            openOauthBean.setAvatar(userInfo.getString("avatar_url"));
        } else {
            throw new Exception();
        }
        return openOauthBean;
    }
}
