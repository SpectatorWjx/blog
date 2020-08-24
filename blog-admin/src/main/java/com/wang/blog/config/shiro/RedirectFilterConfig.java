package com.wang.blog.config.shiro;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RedirectFilterConfig {

    @Bean
    public FilterRegistrationBean registFilter() {

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new AbsoluteSendRedirectFilter());
        registration.addUrlPatterns("*");
        registration.setName("filterRegistrationBean");
        registration.setOrder(1);
        return registration;
    }
}