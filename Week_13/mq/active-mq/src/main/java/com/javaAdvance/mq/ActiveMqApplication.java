package com.javaAdvance.mq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

/**
 * create by hitopei on 2021/1/10 10:21 下午
 * @author hitopei
 */
@SpringBootApplication
@EnableJms
public class ActiveMqApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActiveMqApplication.class, args);
    }
}
