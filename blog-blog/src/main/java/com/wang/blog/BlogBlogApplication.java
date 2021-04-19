package com.wang.blog;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * @author wjx
 * @date 2019/08/13
 */
@EntityScan(basePackages={"com.wang.common.entity.*"})
@EnableJpaRepositories(basePackages = {"com.wang.blog.repository"})
@Slf4j
@SpringBootApplication
@EnableCaching
@CrossOrigin("*")
public class BlogBlogApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(BlogBlogApplication.class, args);
        String serverPort = context.getEnvironment().getProperty("server.port");
        log.info("blog started at http://localhost:" + serverPort);
    }
}
