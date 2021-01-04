package com.javaAdvance.redis.lock.listener;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

/**
 * create by hitopei on 2021/1/3 10:52 下午
 * @author hitopei
 */
public class RedisListener implements MessageListener {

    @Override
    public void onMessage(Message message, byte[] pattern) {
        byte[] body = message.getBody();
        String str = new String(body);
        System.out.println("消费消息为: " + str);
    }
}
