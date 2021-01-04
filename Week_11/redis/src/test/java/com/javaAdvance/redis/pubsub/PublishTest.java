package com.javaAdvance.redis.pubsub;

import com.javaAdvance.redis.lock.RedisLockApplication;
import com.javaAdvance.redis.lock.listener.OrderPublisher;
import com.javaAdvance.redis.lock.listener.ResultBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * create by hitopei on 2021/1/4 10:33 上午
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RedisLockApplication.class)
@Component
public class PublishTest {

    @Autowired
    private OrderPublisher orderPublisher;

    @Test
    public void publish(){
        ResultBean result = new ResultBean();
        result.setCode(1);
        result.setMsg("success");
        result.setData("http://www.baidu.com");
        orderPublisher.publish(result);
    }
}
