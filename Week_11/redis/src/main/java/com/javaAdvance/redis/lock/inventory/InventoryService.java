package com.javaAdvance.redis.lock.inventory;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * create by hitopei on 2021/1/3 4:12 下午
 * @author hitopei
 */
@Service
public class InventoryService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public Long decreaseInventory(Long num){
        Long res = redisTemplate.opsForValue().decrement("inventory_100", num);
        System.out.println("当前剩余库存" + res);
        return res;
    }

    public Boolean initialInventory(Integer num) {
        redisTemplate.opsForValue().set("inventory_100", num.intValue(), 24, TimeUnit.HOURS);
        return Boolean.TRUE;
    }
}
