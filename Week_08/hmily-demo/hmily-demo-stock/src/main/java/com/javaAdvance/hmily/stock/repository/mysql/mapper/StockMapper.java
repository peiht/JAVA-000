package com.javaAdvance.hmily.stock.repository.mysql.mapper;

import com.javaAdvance.hmily.stock.repository.mysql.domain.Stock;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hitopei
 * @since 2020-12-10
 */
public interface StockMapper extends BaseMapper<Stock> {

    @Update("update stock set stock_all = stock_all - #{count}, stock_pre = stock_pre + #{count}" +
            " where goods_id = #{goodsId} and stock_all > 0")
    int decrease(@Param("goodsId") Integer goodsId, @Param("count") Integer count);


    @Update("update stock set stock_pre = stock_pre - #{count} where goods_id = #{goodsId} and stock_pre > 0")
    int confirm(@Param("goodsId") Integer goodsId, @Param("count") Integer count);

    @Update("update stock set stock_all = stock_all + #{count}, stock_pre - #{count}" +
            " where goods_id = #{goodsId} and stock_pre > 0")
    int cancel(@Param("goodsId") Integer goodsId, @Param("count") Integer count);
}
