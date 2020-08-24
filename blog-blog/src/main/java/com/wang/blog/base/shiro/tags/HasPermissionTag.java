package com.wang.blog.base.shiro.tags;

/**
 * @author wjx
 * @date 2019/12/10 11:58
 */
public class HasPermissionTag extends PermissionTag {
    @Override
    protected boolean showTagBody(String p) {
        return isPermitted(p);
    }
}