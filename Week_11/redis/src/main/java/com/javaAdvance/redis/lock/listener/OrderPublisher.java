package com.javaAdvance.redis.lock.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * create by hitopei on 2021/1/3 10:34 下午
 * @author hitopei
 */
@Component
public class OrderPublisher {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 订单处理完成发送异步消息
     * @param result message body
     */
    public void publish(ResultBean result){
        String orderChannel = "order_channel";
        redisTemplate.convertAndSend(orderChannel, result.toString());
    }
}
