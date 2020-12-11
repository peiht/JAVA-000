package com.javaAdvance.hmily.order.web;

import com.javaAdvance.hmily.order.base.ResultBean;
import com.javaAdvance.hmily.order.repository.mysql.domain.GoodsOrder;
import com.javaAdvance.hmily.order.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.soap.Addressing;
import java.math.BigDecimal;

/**
 * @author ty
 *
 * order
 */
@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private PayService payService;

    @RequestMapping("pay")
    public ResultBean pay(@RequestParam("userId") Integer userId,
                          @RequestParam("goodsId") Integer goodsId,
                          @RequestParam("amount") BigDecimal amount,
                          @RequestParam("count") Integer count){
        GoodsOrder order = payService.createOrder(userId, count, amount, goodsId);
        payService.pay(order);
        return ResultBean.success();
    }
}
