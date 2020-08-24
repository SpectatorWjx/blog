package com.wang.blog.base.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Alan_Xiang 
 * 自定义Authenticator
 * 注意，当需要分别定义处理验证的Realm时，对应Realm的全类名应该包含字符串“User”，或者“Othrt”。
 * 并且，他们不能相互包含
 */
public class CustomizedModularRealmAuthenticator extends ModularRealmAuthenticator {

    @Override
    protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        // 判断getRealms()是否返回为空
        assertRealmsConfigured();
        // 强制转换回自定义的CustomizedToken
        CustomizedToken customizedToken = (CustomizedToken) authenticationToken;
        // 登陆类型
        String loginType = customizedToken.getLoginType();
        // 所有Realm
        Collection<Realm> realms = getRealms();
        // 登陆类型对应的所有Realm
        Collection<Realm> typeRealms = new ArrayList<>();
        for (Realm realm : realms) {
            String s = realm.getName();
            if (realm.getName().contains(loginType)) {
                typeRealms.add(realm);
            }
        }

        // 判断是单Realm还是多Realm
        if (typeRealms.size() == 1) {
            return doSingleRealmAuthentication(typeRealms.iterator().next(), customizedToken);
        }else {
            return doMultiRealmAuthentication(typeRealms, customizedToken);
        }
    }

}