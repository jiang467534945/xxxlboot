package com.xxxlboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@SpringBootApplication
@EnableCaching
@EnableAuthorizationServer
@MapperScan("com.xxxlboot.*.mapper*")
public class XxxlBootAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(XxxlBootAdminApplication.class, args);
    }
}
