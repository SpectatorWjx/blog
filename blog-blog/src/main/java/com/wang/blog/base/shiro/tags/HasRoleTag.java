package com.wang.blog.base.shiro.tags;


/**
 * @author wjx
 * @date 2019/12/10 11:58
 */
public class HasRoleTag extends RoleTag {
    @Override
    protected boolean showBody(String roleName) {
        return getSubject() != null && getSubject().hasRole(roleName);
    }
}
