package com.javaAdvance.hmily.order.service;

import com.javaAdvance.hmily.order.base.ResultBean;
import com.javaAdvance.hmily.order.repository.mysql.domain.GoodsOrder;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hitopei
 * @since 2020-12-09
 */
public interface GoodsOrderService extends IService<GoodsOrder> {

    ResultBean<GoodsOrder> createOrder(Integer userId, Integer count, BigDecimal amount, Integer goodsId);

}
