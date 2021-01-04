package com.javaAdvance.redis.lock;

import com.javaAdvance.redis.lock.core.Lock;
import com.javaAdvance.redis.lock.core.RedisLock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * create by hitopei on 2020/12/31 3:50 下午
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RedisLockApplication.class})
@Component
public class TestRedisLock {

    @Autowired
    private Lock lock ;

    @Test
    public void test() throws InterruptedException {
        int threads = 1000;
        ExecutorService service = Executors.newFixedThreadPool(threads);
        CountDownLatch latch = new CountDownLatch(threads);
        for (int i = 0; i < threads; i++) {
            String threadId = String.valueOf(i);
            service.execute(() -> {
                try {
                    testLock("hello1", threadId);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                latch.countDown();
            });
        }
        latch.await();
    }

    public void testLock(String key, String threadId) throws InterruptedException {
        if (lock.tryLock(key)) {
            System.out.println("success get lock :" + threadId);
            Thread.sleep(20);
            lock.unlock(key);
            System.out.println("success unlock :" + threadId);
        } else {
            System.out.println("failed to get lock" + threadId);
        }
    }
}
