package com.javaAdvance.hmily.order.service.impl;

import com.javaAdvance.hmily.order.base.ResultBean;
import com.javaAdvance.hmily.order.repository.mysql.domain.GoodsOrder;
import com.javaAdvance.hmily.order.repository.mysql.mapper.GoodsOrderMapper;
import com.javaAdvance.hmily.order.service.GoodsOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Random;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hitopei
 * @since 2020-12-09
 */
@Service
public class GoodsOrderServiceImpl extends ServiceImpl<GoodsOrderMapper, GoodsOrder> implements GoodsOrderService {

    @Override
    public ResultBean<GoodsOrder> createOrder(Integer userId, Integer count, BigDecimal amount, Integer goodsId) {
        Random random = new Random();
        GoodsOrder goodsOrder = new GoodsOrder();
        goodsOrder.setOrderName("orderName" + random.nextInt(100));
        goodsOrder.setOrderStatus(0);
        goodsOrder.setUserId(userId);
        goodsOrder.setTradeAmount(amount.multiply(BigDecimal.valueOf(count)).toString());
        goodsOrder.setGoodsId(goodsId);
        goodsOrder.setCount(count);
        goodsOrder.setSignalPrice(amount);
        this.save(goodsOrder);
        return ResultBean.success(goodsOrder);
    }
}
