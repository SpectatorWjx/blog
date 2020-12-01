package com.wang.blog.base.oauth.utils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;


/**
 * @author wjx
 * @date 2019/12/10
 */
public class TrustAnyHostnameVerifier implements HostnameVerifier {
    @Override
    public boolean verify(String hostname, SSLSession session) {
        return true;
    }
}
