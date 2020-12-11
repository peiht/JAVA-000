package com.javaAdvance.hmily.order.repository.mysql.mapper;

import com.javaAdvance.hmily.order.repository.mysql.domain.GoodsOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hitopei
 * @since 2020-12-09
 */
@Repository
public interface GoodsOrderMapper extends BaseMapper<GoodsOrder> {

//    @Update("update goods_order set order_status = #{1} where " +
//            "order_id = #{0}")
    int update(@Param("orderId") Long orderId, @Param("orderStatus") Integer orderStatus);

}
