package com.javaAdvance.mq.queue;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * create by hitopei on 2021/1/10 6:01 下午
 */
@Component
public class QueueConsumer {

    @JmsListener(destination = "test.queue", containerFactory = "queueListener")
    public void receive(String message){
        System.out.println("receive queue message : " + message);
    }
}
