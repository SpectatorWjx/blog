package com.wang.blog.base.shiro;

import com.wang.blog.base.lang.Consts;
import com.wang.blog.vo.AccountProfile;
import com.wang.blog.vo.UserVO;
import com.wang.blog.service.UserRoleService;
import com.wang.blog.service.UserService;
import com.wang.common.entity.user.RoleEntity;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.AllowAllCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author wjx
 * @date 2019/12/10
 */
public class OtherRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;

    public OtherRealm() {
        super(new AllowAllCredentialsMatcher());
        setAuthenticationTokenClass(UsernamePasswordToken.class);
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        AccountProfile profile = (AccountProfile) SecurityUtils.getSubject().getPrincipal();
        if (profile != null) {
            UserVO user = userService.get(profile.getId());
            if (user != null) {
                SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
                List<RoleEntity> roles = userRoleService.listRoles(user.getId());
                //赋予角色
                roles.forEach(role -> {
                    info.addRole(role.getName());
                    //赋予权限
                    role.getPermissions().forEach(permission -> info.addStringPermission(permission.getName()));
                });
                return info;
            }
        }
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        AccountProfile profile = getAccount(userService, token);

        if (profile.getStatus() == Consts.STATUS_CLOSED) {
            throw new LockedAccountException(profile.getName());
        }

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(profile, token.getCredentials(), getName());
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute("profile", profile);
        return info;
    }

    protected AccountProfile getAccount(UserService userService, AuthenticationToken token) {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        return userService.loginOther(upToken.getUsername(), String.valueOf(upToken.getPassword()));
    }
}
