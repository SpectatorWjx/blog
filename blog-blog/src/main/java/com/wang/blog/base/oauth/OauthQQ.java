package com.wang.blog.base.oauth;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wang.blog.base.oauth.utils.EnumOauthTypeBean;
import com.wang.blog.base.oauth.utils.OathConfig;
import com.wang.blog.base.oauth.utils.OpenOauthBean;
import com.wang.blog.base.oauth.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class OauthQQ extends Oauth {
    private static final String AUTH_URL = "https://graph.qq.com/oauth2.0/authorize";
    private static final String TOKEN_URL = "https://graph.qq.com/oauth2.0/token";
    private static final String TOKEN_INFO_URL = "https://graph.qq.com/oauth2.0/me";
    private static final String USER_INFO_URL = "https://graph.qq.com/user/get_user_info";

    public static OauthQQ me() {
        return new OauthQQ();
    }

    public OauthQQ() {
        setClientId(OathConfig.getValue("openid_qq"));
        setClientSecret(OathConfig.getValue("openkey_qq"));
        setRedirectUri(OathConfig.getValue("redirect_qq"));
    }

    public String getAuthorizeUrl(String state) throws UnsupportedEncodingException {
        Map<String, String> params = new HashMap<>(4);
        params.put("response_type", "code");
        params.put("client_id", getClientId());
        params.put("redirect_uri", getRedirectUri());
        if (StringUtils.isNotBlank(state)) {
            params.put("state", state);
        }
        return super.getAuthorizeUrl(AUTH_URL, params);
    }

    public String getUserInfo(String accessToken, String uid) throws IOException, KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException {
        Map<String, String> params = new HashMap<>(4);
        params.put("access_token", accessToken);
        params.put("oauth_consumer_key", getClientId());
        params.put("openid", uid);
        params.put("format", "json");
        String userInfo = super.doGet(USER_INFO_URL, params);
        log.debug(userInfo);
        return userInfo;
    }

    public JSONObject getUserInfoByCode(String code) throws IOException, KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException {
        Map<String, String> params = new HashMap<>(5);
        params.put("code", code);
        params.put("client_id", getClientId());
        params.put("client_secret", getClientSecret());
        params.put("grant_type", "authorization_code");
        params.put("redirect_uri", getRedirectUri());
        Map<String, String> tokenMap = TokenUtil.getAuthToken(super.doGet(TOKEN_URL, params));
        //获取openid
        String accessToken = tokenMap.get("accessToken");
        Map<String, String> params2 = new HashMap<>(1);
        params2.put("access_token", accessToken);
        String openid = TokenUtil.getOpenId(super.doGet(TOKEN_INFO_URL, params2));
        //获取用户信息
        if (StringUtils.isBlank(accessToken)) {
            return null;
        }
        if (StringUtils.isBlank(openid)) {
            return null;
        }
        JSONObject dataMap = JSON.parseObject(getUserInfo(accessToken, openid));
        dataMap.put("openid", openid);
        dataMap.putAll(tokenMap);
        log.debug(JSON.toJSONString(dataMap));
        return dataMap;
    }

    @Override
    public OpenOauthBean getUserBeanByCode(String code)
            throws Exception {
        OpenOauthBean openOauthBean = null;
        JSONObject userInfo = me().getUserInfoByCode(code);
        if (userInfo != null && userInfo.getString("ret").equals("0")) {
            openOauthBean = new OpenOauthBean();
            String username = "QQ" + TokenUtil.md5(userInfo.getString("openid"));
            openOauthBean.setAccessToken(userInfo.getString("accessToken"));
            openOauthBean.setExpireIn(userInfo.getString("expiresIn"));
            openOauthBean.setRefreshToken(userInfo.getString("refreshToken"));
            openOauthBean.setOauthUserId(userInfo.getString("openid"));
            openOauthBean.setOauthType(EnumOauthTypeBean.TYPE_QQ.getValue());
            openOauthBean.setUsername(username);
            openOauthBean.setNickname(userInfo.getString("nickname"));
            openOauthBean.setAvatar(userInfo.getString("figureurl"));
        } else {
            throw new Exception();
        }
        return openOauthBean;
    }
}
