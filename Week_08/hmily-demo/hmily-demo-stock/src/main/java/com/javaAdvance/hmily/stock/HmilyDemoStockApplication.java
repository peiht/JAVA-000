package com.javaAdvance.hmily.stock;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ImportResource;

/**
 * @author hitopei
 *
 * hmily stock
 */
@SpringBootApplication(exclude = {MongoAutoConfiguration.class})
@EnableEurekaClient
@EnableFeignClients
@MapperScan(basePackages = "com.javaAdvance.hmily.stock.repository.mysql.mapper")
@ImportResource({"classpath:applicationContext.xml"})
public class HmilyDemoStockApplication {
    public static void main(String[] args) {
        SpringApplication.run(HmilyDemoStockApplication.class, args);
    }
}
