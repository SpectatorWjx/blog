package com.wang.blog.base.shiro.tags;

/**
 * @author wjx
 * @date 2019/12/10 11:58
 */
public class LacksPermissionTag extends PermissionTag {
    @Override
    protected boolean showTagBody(String p) {
        return !isPermitted(p);
    }
}
