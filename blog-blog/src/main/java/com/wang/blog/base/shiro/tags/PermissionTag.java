package com.wang.blog.base.shiro.tags;

import com.wang.blog.base.lang.Consts;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModelException;

import java.io.IOException;
import java.util.Map;

/**
 * @author wjx
 * @date 2019/12/10
 */
public abstract class PermissionTag extends SecureTag {
    String getName(Map params) {
        return getParam(params, "name");
    }

    @Override
    protected void verifyParameters(Map params) throws TemplateModelException {
        String permission = getName(params);

        if (permission == null || permission.length() == 0) {
            throw new TemplateModelException("The 'name' tag attribute must be set.");
        }
    }

    @Override
    public void render(Environment env, Map params, TemplateDirectiveBody body) throws IOException, TemplateException {
        String p = getName(params);

        boolean show = showTagBody(p);
        if (show) {
            renderBody(env, body);
        }
    }

    /**
     * 判断是否拥有指定权限
     * - 超级管理员例外, 拥有所有
     * @param p 权限代码
     * @return true/false
     */
    protected boolean isPermitted(String p) {
        return getSubject() != null && (getSubject().hasRole(Consts.ROLE_ADMIN) || getSubject().isPermitted(p));
    }

    protected abstract boolean showTagBody(String p);
}
