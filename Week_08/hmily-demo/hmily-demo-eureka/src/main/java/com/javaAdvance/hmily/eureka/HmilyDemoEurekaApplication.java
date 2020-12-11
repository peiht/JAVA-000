package com.javaAdvance.hmily.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author hitopei
 *
 * eureka注册中心
 */
@SpringBootApplication
@EnableEurekaServer
public class HmilyDemoEurekaApplication {
    public static void main(String[] args) {
        SpringApplication.run(HmilyDemoEurekaApplication.class, args);
    }
}
