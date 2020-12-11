package com.javaAdvance.hmily.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author hitopei
 *
 * hmily user
 */
@EnableEurekaClient
@SpringBootApplication(exclude = {MongoAutoConfiguration.class})
@MapperScan(basePackages = "com.javaAdvance.hmily.user.repository.mysql.mapper")
public class HmilyDemoUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(HmilyDemoUserApplication.class, args);
    }
}
