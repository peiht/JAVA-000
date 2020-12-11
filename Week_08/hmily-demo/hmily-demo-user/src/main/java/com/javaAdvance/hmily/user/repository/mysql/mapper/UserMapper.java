package com.javaAdvance.hmily.user.repository.mysql.mapper;

import com.javaAdvance.hmily.user.repository.mysql.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hitopei
 * @since 2020-12-09
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Update("update user set balance = balance - #{amount}, freeze_amount = freeze_amount + #{amount}" +
            " where id = #{userId} and balance > 0 ")
    int updateAccount(Integer userId, BigDecimal amount);

    @Update("update user set freeze_amount = freeze_amount - #{amount}" +
            " where id = #{userId} and freeze_amount > 0")
    int confirm(Integer userId, BigDecimal amount);

    @Update("update user set balance = balance + #{amount}, freeze_amount = freeze_amount - #{amount}" +
            "where id = #{userId} and freeze_amount > 0")
    int cancel(Integer userId, BigDecimal amount);
}
