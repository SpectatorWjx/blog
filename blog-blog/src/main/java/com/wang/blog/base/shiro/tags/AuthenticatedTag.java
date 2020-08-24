package com.wang.blog.base.shiro.tags;

import freemarker.core.Environment;
import freemarker.log.Logger;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import org.apache.shiro.subject.Subject;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;


/**
 * @author wjx
 * @date 2019/12/10 11:58
 */
public class AuthenticatedTag extends SecureTag {
    private static final Logger log = Logger.getLogger("AuthenticatedTag");

    @Override
    public void render(Environment env, Map params, TemplateDirectiveBody body) throws IOException, TemplateException {
        Subject subject = getSubject();
        if (Optional.ofNullable(subject).isPresent() && (getSubject().isAuthenticated() || getSubject().isRemembered())) {
            if (log.isDebugEnabled()) {
                log.debug("Subject exists and is authenticated.  Tag body will be evaluated.");
            }

            renderBody(env, body);
        } else {
            if (log.isDebugEnabled()) {
                log.debug("Subject does not exist or is not authenticated.  Tag body will not be evaluated.");
            }
        }
    }
}
