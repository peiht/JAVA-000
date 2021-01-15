package com.javaAdvance.mq.topic;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * create by hitopei on 2021/1/10 10:47 下午
 */
@Component
public class TopicConsumer {

    @JmsListener(destination = "topic", containerFactory = "topicListener")
    public void receive(String message){
        System.out.println("receive one message ：" + message);
    }
}
