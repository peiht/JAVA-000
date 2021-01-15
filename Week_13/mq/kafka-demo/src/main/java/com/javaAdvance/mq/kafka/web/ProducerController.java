package com.javaAdvance.mq.kafka.web;


import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author hitopei
 * create by hitopei on 2021/1/13 2:02 下午
 */
@RestController
public class ProducerController {
    @Resource
    private KafkaTemplate<String, Object> kafkaTemplate;

    @RequestMapping("produce")
    public String produce() {
        kafkaTemplate.send("kafka", "hello");
        return "success";
    }

    @RequestMapping("produceFuture")
    public String produceFuture() {
        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send("kafka", "what's your name?");
        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onFailure(Throwable ex) {
                System.out.println("发送消息发生错误:" + ex.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, Object> result) {
                System.out.println("发送消息成功：" + result.getRecordMetadata().toString());
            }
        });
        return "success";
    }
}
