package com.javaAdvance.mq.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Queue;
import javax.jms.Topic;

/**
 * create by hitopei on 2021/1/10 6:00 下午
 * @author hitopei
 */
@RestController
public class QueueProducer {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    @Autowired
    private Queue queue;

    @RequestMapping("queue")
    public void produce(){
        jmsMessagingTemplate.convertAndSend(queue, "queue message");
    }
}
