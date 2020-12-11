package com.javaAdvance.hmily.user.client;

import com.alibaba.fastjson.JSONObject;
import com.javaAdvance.hmily.user.base.ResultBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author hitopei
 *
 *
 */
//@FeignClient(value = "stock-service")
public interface StockFeign {

    /**
     * 减库存接口
     * @param data
     * @return
     */
    @RequestMapping("/hmily-stock/stock/decrease")
    ResultBean decreaseStock(@RequestBody JSONObject data);
}
