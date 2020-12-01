package com.wang.blog.base.oauth;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wang.blog.base.oauth.utils.EnumOauthTypeBean;
import com.wang.blog.base.oauth.utils.OathConfig;
import com.wang.blog.base.oauth.utils.OpenOauthBean;
import com.wang.blog.base.oauth.utils.TokenUtil;
import com.wang.blog.base.utils.RestTemplateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

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
@Slf4j
public class OauthGitee extends Oauth {
    private static final String AUTH_URL = "https://gitee.com/oauth/authorize";
    private static final String TOKEN_URL = "https://gitee.com/oauth/token";
    private static final String USER_INFO_URL = "https://gitee.com/api/v5/user";

    public static OauthGitee me() {
        return new OauthGitee();
    }

    public OauthGitee() {
        setClientId(OathConfig.getValue("openid_gitee"));
        setClientSecret(OathConfig.getValue("openkey_gitee"));
        setRedirectUri(OathConfig.getValue("redirect_gitee"));
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

    public JSONObject getUserInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("user-agent", "Chrome/69.0.3497.81 Safari/537.36");
        ResponseEntity<String> responseEntity =  RestTemplateUtils.get(USER_INFO_URL+"?access_token="+accessToken, headers, String.class);
        String userInfo = "";
        if (responseEntity != null && responseEntity.getStatusCodeValue() == 200) {
            userInfo = responseEntity.getBody();
        }
        JSONObject dataMap = JSON.parseObject(userInfo);
        log.debug(dataMap.toJSONString());
        return dataMap;
    }

    public JSONObject getUserInfoByCode(String code)
            throws IOException, KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException {
        Map<String, String> params = new HashMap<>();
        params.put("code", code);
        params.put("client_id", getClientId());
        params.put("client_secret", getClientSecret());
        params.put("redirect_uri",getRedirectUri());
        params.put("grant_type","authorization_code");
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        Map<String, String> tokenMap = TokenUtil.getAuthToken(super.doPost(TOKEN_URL, params, headers));
        String accessToken = tokenMap.get("accessToken");
        if (StringUtils.isBlank(accessToken)) {
            return null;
        }
        JSONObject dataMap = getUserInfo(accessToken);
        dataMap.putAll(tokenMap);
        log.debug(JSON.toJSONString(dataMap));
        return dataMap;
    }

    @Override
    public OpenOauthBean getUserBeanByCode(String code)
            throws Exception {
        OpenOauthBean openOauthBean = null;
        JSONObject userInfo = me().getUserInfoByCode(code);

        if (userInfo != null) {
            openOauthBean = new OpenOauthBean();
            String username = "GITEE" + TokenUtil.md5(userInfo.getString("id"));
            openOauthBean.setAccessToken(userInfo.getString("accessToken"));
            openOauthBean.setExpireIn(userInfo.getString("expiresIn"));
            openOauthBean.setRefreshToken(userInfo.getString("refreshToken"));
            openOauthBean.setOauthUserId(userInfo.getString("id"));
            openOauthBean.setOauthType(EnumOauthTypeBean.TYPE_GITEE.getValue());
            openOauthBean.setUsername(username);
            openOauthBean.setNickname(userInfo.getString("name"));
            openOauthBean.setAvatar(userInfo.getString("avatar_url"));
        } else {
            throw new Exception();
        }
        return openOauthBean;
    }
}
