package com.wang.blog.base.shiro;

/**
 * @author wjx
 */

public enum LoginType {
    /**
     * user
     */
    USER("Account"),
    /**
     *
     */
    OTHER("Other");

    private String type;

    private LoginType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type.toString();
    }
}