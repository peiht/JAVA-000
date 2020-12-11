package com.javaAdvance.hmily.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.javaAdvance.hmily.order.base.ResultBean;
import com.javaAdvance.hmily.order.client.StockFeign;
import com.javaAdvance.hmily.order.client.UserFeign;
import com.javaAdvance.hmily.order.repository.mysql.domain.GoodsOrder;
import com.javaAdvance.hmily.order.repository.mysql.mapper.GoodsOrderMapper;
import com.javaAdvance.hmily.order.service.GoodsOrderService;
import com.javaAdvance.hmily.order.service.PayService;
import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author hitopei
 *
 * 支付
 */
@Service
public class PayServiceImpl implements PayService {

    @Autowired
    private GoodsOrderService goodsOrderService;
    @Autowired
    private GoodsOrderMapper orderMapper;
    @Autowired
    private StockFeign stockFeign;
    @Autowired
    private UserFeign userFeign;

    @Override
    @HmilyTCC(confirmMethod = "confirm", cancelMethod = "cancel")
    public ResultBean pay(GoodsOrder order) {
        //修改订单状态
        updateProcess(order);
        //扣减用户表的余额 
        JSONObject accountData = new JSONObject();
        accountData.put("userId", order.getUserId());
        accountData.put("amount", order.getSignalPrice());
        accountData.put("count", order.getCount());
        boolean res1 = userFeign.accountPay(accountData);
        System.out.println("调用扣款完成");
        //扣减库存表库存
        JSONObject stockData = new JSONObject();
        stockData.put("goodsId", order.getGoodsId());
        stockData.put("count", order.getCount());
        boolean res2 = stockFeign.decreaseStock(stockData);
        System.out.println("调用扣库存完成");
        return ResultBean.success();
    }

    @Override
    public GoodsOrder createOrder(Integer userId, Integer count, BigDecimal amount, Integer goodsId) {
        ResultBean<GoodsOrder> resultBean = goodsOrderService.createOrder(userId, count, amount, goodsId);
        return resultBean.getData();
    }

    public void updateProcess(GoodsOrder order){
        orderMapper.update(order.getOrderId(), 1);
    }

    public ResultBean confirm(GoodsOrder order) {
        orderMapper.update(order.getOrderId(), 2);
        System.out.println("订单确认");
        return ResultBean.success();
    }

    public ResultBean cancel(GoodsOrder order) {
        orderMapper.update(order.getOrderId(), 3);
        System.out.println("订单取消");
        return ResultBean.success();
    }
}
