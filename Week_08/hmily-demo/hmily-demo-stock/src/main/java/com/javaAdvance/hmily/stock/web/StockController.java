package com.javaAdvance.hmily.stock.web;

import com.alibaba.fastjson.JSONObject;
import com.javaAdvance.hmily.stock.base.ResultBean;
import com.javaAdvance.hmily.stock.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ty
 */
@RestController
@RequestMapping("stock")
public class StockController {

    @Autowired
    private StockService stockService;

    @RequestMapping("dealStock")
    public ResultBean dealStock(@RequestParam("stockId") Integer stockId,
                                @RequestParam("count") Integer count){
        return ResultBean.success();
    }

    /**
     * 减库存
     * @param data
     * @return
     */
    @RequestMapping("decrease")
    public Boolean decrease(@RequestBody JSONObject data){
        return stockService.decrease(data);
    }
}
