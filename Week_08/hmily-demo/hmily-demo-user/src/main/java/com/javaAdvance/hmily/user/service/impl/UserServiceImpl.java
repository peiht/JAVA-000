package com.javaAdvance.hmily.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.javaAdvance.hmily.user.base.ResultBean;
import com.javaAdvance.hmily.user.repository.mysql.domain.User;
import com.javaAdvance.hmily.user.repository.mysql.mapper.UserMapper;
import com.javaAdvance.hmily.user.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hitopei
 * @since 2020-12-09
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    @HmilyTCC(confirmMethod = "confirm", cancelMethod = "cancel")
    public Boolean payAccount(JSONObject data) {
        Integer userId = data.getInteger("userId");
        BigDecimal amount = data.getBigDecimal("amount");
        Integer count = data.getInteger("count");
        BigDecimal amountAll = BigDecimal.valueOf(count).add(amount);
        this.baseMapper.updateAccount(userId, amountAll);
        return Boolean.TRUE;
    }


    public Boolean confirm(JSONObject data){
        Integer userId = data.getInteger("userId");
        BigDecimal amount = data.getBigDecimal("amount");
        Integer count = data.getInteger("count");
        BigDecimal amountAll = BigDecimal.valueOf(count).add(amount);
        System.out.println("账户确认扣款");
        this.baseMapper.confirm(userId, amountAll);
        return Boolean.TRUE;
    }

    public Boolean cancel(JSONObject data){
        Integer userId = data.getInteger("userId");
        BigDecimal amount = data.getBigDecimal("amount");
        Integer count = data.getInteger("count");
        BigDecimal amountAll = BigDecimal.valueOf(count).add(amount);
        System.out.println("账户取消扣款");
        this.baseMapper.cancel(userId, amountAll);
        return Boolean.TRUE;
    }
}
