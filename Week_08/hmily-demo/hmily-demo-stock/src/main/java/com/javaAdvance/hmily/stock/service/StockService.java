package com.javaAdvance.hmily.stock.service;

import com.alibaba.fastjson.JSONObject;
import com.javaAdvance.hmily.stock.base.ResultBean;
import com.javaAdvance.hmily.stock.repository.mysql.domain.Stock;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sun.org.apache.xpath.internal.operations.Bool;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hitopei
 * @since 2020-12-10
 */
public interface StockService extends IService<Stock> {

    Boolean decrease(JSONObject data);
}
