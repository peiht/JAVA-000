package com.javaAdvance.hmily.order.service;

import com.javaAdvance.hmily.order.base.ResultBean;
import com.javaAdvance.hmily.order.repository.mysql.domain.GoodsOrder;

import java.math.BigDecimal;

public interface PayService {

    ResultBean pay(GoodsOrder order);

    GoodsOrder createOrder(Integer userId, Integer count, BigDecimal amount, Integer goodsId);
}
