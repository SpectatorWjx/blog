package com.wang.blog.base.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.wang.blog.modules.hook.interceptor.BaseInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author wjx
 * @date 2019/12/10
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    @Autowired
    private BaseInterceptor baseInterceptor;
    @Autowired
    private FastJsonHttpMessageConverter fastJsonHttpMessageConverter;

    /**
     * Add intercepter
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(baseInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/dist/**", "/static/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/dist/**")
                .addResourceLocations("classpath:/static/dist/");
        registry.addResourceHandler("/theme/*/dist/**")
                .addResourceLocations("classpath:/templates/");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(fastJsonHttpMessageConverter);
    }


    @Bean(name = "myRestTemplate")
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
