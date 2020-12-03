package com.javaAdvance;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author hitopei
 *
 * springboot 启动类
 */
@MapperScan(basePackages = "com.javaAdvance.repository.mysql.mapper")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class DataSourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataSourceApplication.class, args);
    }
}
