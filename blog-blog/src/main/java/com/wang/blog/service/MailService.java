package com.wang.blog.service;

import java.util.Map;

/**
 * @author : wjx
 */
public interface MailService {
    /**
     * 配置
     */
    void config();

    /**
     * 发邮件
     * @param to
     * @param title
     * @param template
     * @param content
     */
    void sendTemplateEmail(String to, String title, String template, Map<String, Object> content);
}
