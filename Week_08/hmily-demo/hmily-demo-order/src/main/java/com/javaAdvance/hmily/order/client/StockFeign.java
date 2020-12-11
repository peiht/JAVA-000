package com.javaAdvance.hmily.order.client;

import com.alibaba.fastjson.JSONObject;
import com.javaAdvance.hmily.order.base.ResultBean;
import org.dromara.hmily.annotation.Hmily;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author hitopei
 */
@FeignClient(value = "hmily-stock")
public interface StockFeign {

    /**
     * 减库存接口
     * @param data
     * @return
     */
    @Hmily
    @RequestMapping("/hmily-stock/stock/decrease")
    Boolean decreaseStock(@RequestBody JSONObject data);
}
