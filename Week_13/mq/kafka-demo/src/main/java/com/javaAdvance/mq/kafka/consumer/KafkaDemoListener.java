package com.javaAdvance.mq.kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author hitopei
 */
@Component
public class KafkaDemoListener {

    @KafkaListener(id = "webGroup", topics = "kafka")
    public void consumer(String message){
        System.out.println("消费端接受到消息为" + message);
    }
}

