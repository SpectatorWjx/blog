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
public class OauthSina extends Oauth {
    private static final String AUTH_URL = "https://api.weibo.com/oauth2/authorize";
    private static final String TOKEN_URL = "https://api.weibo.com/oauth2/access_token";
    private static final String TOKEN_INFO_URL = "https://api.weibo.com/oauth2/get_token_info";
    private static final String USER_INFO_URL = "https://api.weibo.com/2/users/show.json";

    public static OauthSina me() {
        return new OauthSina();
    }

    public OauthSina() {
        setClientId(OathConfig.getValue("openid_sina"));
        setClientSecret(OathConfig.getValue("openkey_sina"));
        setRedirectUri(OathConfig.getValue("redirect_sina"));
    }

    public String getAuthorizeUrl(String state) throws UnsupportedEncodingException {
        Map<String, String> params = new HashMap<>();
        if (StringUtils.isNotBlank(state)) {
            params.put("state", state);
        }
        params.put("response_type", "code");
        params.put("client_id", getClientId());
        params.put("redirect_uri", getRedirectUri());
        return super.getAuthorizeUrl(AUTH_URL, params);
    }

    public String getUserInfo(String accessToken, String uid) throws IOException, KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException {
        Map<String, String> params = new HashMap<>();
        params.put("uid", uid);
        params.put("access_token", accessToken);
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
        Map<String, String> tokenMap = TokenUtil.getAuthToken(super.doPost(TOKEN_URL, params));
        //获取openid
        String accessToken = tokenMap.get("accessToken");
        Map<String, String> params2 = new HashMap<>(1);
        params2.put("access_token", accessToken);
        String openid = TokenUtil.getUid(super.doPost(TOKEN_INFO_URL, params2));
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

        if (userInfo != null) {
            openOauthBean = new OpenOauthBean();
            String username = "SN" + TokenUtil.md5(userInfo.getString("openid"));
            openOauthBean.setAccessToken(userInfo.getString("accessToken"));
            openOauthBean.setExpireIn(userInfo.getString("expiresIn"));
            openOauthBean.setRefreshToken(userInfo.getString("refreshToken"));
            openOauthBean.setOauthUserId(userInfo.getString("openid"));
            openOauthBean.setOauthType(EnumOauthTypeBean.TYPE_SINA.getValue());
            openOauthBean.setUsername(username);
            openOauthBean.setNickname(userInfo.getString("screen_name"));
            openOauthBean.setAvatar(userInfo.getString("profile_image_url"));
        } else {
            throw new Exception();
        }
        return openOauthBean;
    }
}
