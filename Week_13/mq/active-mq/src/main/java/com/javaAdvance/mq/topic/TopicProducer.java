package com.javaAdvance.mq.topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Topic;

/**
 * create by hitopei on 2021/1/10 10:47 下午
 */
@RestController
public class TopicProducer {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    @Autowired
    private Topic topic;

    @RequestMapping("send")
    public String send(){
        String body = "test topic";
        jmsMessagingTemplate.convertAndSend(topic, body);
        return "1";
    }
}
