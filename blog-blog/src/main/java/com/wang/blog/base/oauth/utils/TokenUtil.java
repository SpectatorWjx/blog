package com.wang.blog.base.oauth.utils;

import com.alibaba.fastjson.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * @author wjx
 * @date 2019/12/10 11:58
 */
public class TokenUtil {
    private static final String STR_S = "abcdefghijklmnopqrstuvwxyz0123456789";

    public static Map<String, String> getAuthToken(String string) {
        Map<String, String> tokenMap = new HashMap<>(3);
        try {
            JSONObject json = JSONObject.parseObject(string);
            if (json != null) {
                tokenMap.put("accessToken", json.getString("access_token"));
                tokenMap.put("expiresIn", json.getString("expires_in"));
                tokenMap.put("refreshToken", json.getString("refresh_token"));
            }
        } catch (Exception e) {
            Matcher m = java.util.regex.Pattern.compile("^access_token=(\\w+)&expires_in=(\\w+)&refresh_token=(\\w+)$").matcher(string);
            if (m.find()) {
                tokenMap.put("accessToken", m.group(1));
                tokenMap.put("expiresIn", m.group(2));
                tokenMap.put("refreshToken", m.group(3));
            } else {
                Matcher m2 = java.util.regex.Pattern.compile("^access_token=(\\w+)&expires_in=(\\w+)$").matcher(string);
                if (m2.find()) {
                    tokenMap.put("accessToken", m.group(1));
                    tokenMap.put("expiresIn", m.group(2));
                }
            }
        }
        return tokenMap;
    }

    public static String getOpenId(String string) {
        String openid = null;
        Matcher m = java.util.regex.Pattern.compile("\"openid\"\\s*:\\s*\"(\\w+)\"").matcher(string);
        if (m.find()) {
            openid = m.group(1);
        }
        return openid;
    }

    public static String getUid(String string) {
        JSONObject json = JSONObject.parseObject(string);
        return json.getString("uid");
    }

    public static String randomState() {
        return org.apache.commons.lang3.RandomStringUtils.random(24, STR_S);
    }

    public static String md5(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte[] mdByte = md.digest();
            int i;
            StringBuilder buf = new StringBuilder("");
            for (byte value : mdByte) {
                i = value;
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            return buf.toString().substring(8, 24);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return plainText;
    }
}
