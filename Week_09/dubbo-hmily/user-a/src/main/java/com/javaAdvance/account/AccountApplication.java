package com.javaAdvance.account;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author hitopei
 * 启动类
 */
@MapperScan({"com.javaAdvance.account.api.repository.mysql.mapper"})
@SpringBootApplication(exclude = {MongoAutoConfiguration.class})
@EnableTransactionManagement
public class AccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountApplication.class, args);
    }
}
