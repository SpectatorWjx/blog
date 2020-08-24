package com.wang.blog.service;

import java.util.Map;

/**
 * @author : wjx
 */
public interface MailService {
    void config();
    void sendTemplateEmail(String to, String title, String template, Map<String, Object> content);
}
