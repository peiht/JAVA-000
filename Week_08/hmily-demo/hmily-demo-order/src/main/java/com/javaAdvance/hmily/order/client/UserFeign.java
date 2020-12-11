package com.javaAdvance.hmily.order.client;

import com.alibaba.fastjson.JSONObject;
import com.javaAdvance.hmily.order.base.ResultBean;
import org.dromara.hmily.annotation.Hmily;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "hmily-user")
public interface UserFeign {

    @RequestMapping("/hmily-user/user/pay")
    @Hmily
    Boolean accountPay(@RequestBody JSONObject data);
}
