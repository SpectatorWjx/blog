package com.wang.blog.base.shiro.tags;


/**
 * @author wjx
 * @date 2019/12/10
 */
public class LacksRoleTag extends RoleTag {
    @Override
    protected boolean showBody(String roleName) {
        boolean hasRole = getSubject() != null && getSubject().hasRole(roleName);
        return !hasRole;
    }
}
