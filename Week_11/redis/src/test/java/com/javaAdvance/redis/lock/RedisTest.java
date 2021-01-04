package com.javaAdvance.redis.lock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * create by hitopei on 2020/12/31 11:17 上午
 */
@SpringBootTest(classes = {RedisLockApplication.class})
@RunWith(SpringJUnit4ClassRunner.class)
@Component
public class RedisTest {
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void set(){
        redisTemplate.opsForValue().set("hello", "world");
    }
}
