package com.javaAdvance.redis.lock.listener;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

/**
 * create by hitopei on 2021/1/3 10:51 下午
 * @author hitopei
 */
@Configuration
public class RedisPubSubConfig {

    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory factory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(factory);
        container.addMessageListener(new RedisListener(), new PatternTopic("order_channel"));
        return container;
    }
}
