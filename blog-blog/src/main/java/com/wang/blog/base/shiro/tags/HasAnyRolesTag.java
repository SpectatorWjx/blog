package com.wang.blog.base.shiro.tags;

import org.apache.shiro.subject.Subject;


/**
 * @author wjx
 * @date 2019/12/10 11:58
 */
public class HasAnyRolesTag extends RoleTag {
    private static final String ROLE_NAMES_DELIMETER = ",";

    @Override
    protected boolean showBody(String roleNames) {
        boolean hasAnyRole = false;
        Subject subject = getSubject();

        if (subject != null) {
            // Iterate through roles and check to see if the user has one of the roles
            for (String role : roleNames.split(ROLE_NAMES_DELIMETER)) {
                if (subject.hasRole(role.trim())) {
                    hasAnyRole = true;
                    break;
                }
            }
        }

        return hasAnyRole;
    }
}
