package com.wang.blog;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author zhijiu_w
 */
@EntityScan(basePackages={"com.wang.common.entity.*"})
@EnableJpaRepositories(basePackages = {"com.wang.blog.repository"})
@Slf4j
@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class} )
@EnableCaching
public class BlogAdminApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(BlogAdminApplication.class, args);
        String serverPort = context.getEnvironment().getProperty("server.port");
        log.info("blog admin started at http://localhost:" + serverPort);
    }

}
