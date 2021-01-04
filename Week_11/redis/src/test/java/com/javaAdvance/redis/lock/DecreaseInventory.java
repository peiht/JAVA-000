package com.javaAdvance.redis.lock;

import com.javaAdvance.redis.lock.inventory.InventoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * create by hitopei on 2021/1/3 2:02 下午
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {RedisLockApplication.class})
@Component
public class DecreaseInventory {

    @Autowired
    private InventoryService inventoryService;

    @Test
    public void decrease(){
        Random random = new Random();
        ExecutorService service = Executors.newFixedThreadPool(1);
        service.execute(() -> {
            int num = random.nextInt(10);
            Long numLong = Long.parseLong(String.valueOf(num));
            Long inventoryNow = inventoryService.decreaseInventory(numLong);
            System.out.println("当前剩余库存为:" + inventoryNow);
        });
//        Long res = inventoryService.decreaseInventory(10L);
//        System.out.println(res);
    }

    @Test
    public void init(){
        Boolean res = inventoryService.initialInventory(1000);
        System.out.println(res);
    }
}
