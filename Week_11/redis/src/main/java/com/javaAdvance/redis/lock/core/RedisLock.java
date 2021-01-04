package com.javaAdvance.redis.lock.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author hitopei
 * create by hitopei on 2020/12/31 2:22 下午
 */
@Component
public class RedisLock implements Lock{

    private static final int LOCK_EXPIRE = 5000;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public Boolean tryLock(String key) {
        return redisTemplate.opsForValue().setIfAbsent(key, "test", LOCK_EXPIRE, TimeUnit.MILLISECONDS);
    }

    @Override
    public void unlock(String key) {
        redisTemplate.delete(key);
    }
}
