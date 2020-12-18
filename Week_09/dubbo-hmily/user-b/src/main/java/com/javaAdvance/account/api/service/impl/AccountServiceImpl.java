package com.javaAdvance.account.api.service.impl;

import com.javaAdvance.account.api.repository.mysql.mapper.AccountFreezeMapper;
import com.javaAdvance.account.api.repository.mysql.mapper.AccountRmbMapper;
import com.javaAdvance.account.api.repository.mysql.mapper.AccountUsdMapper;
import com.javaAdvance.account.api.service.AccountService;
import org.apache.dubbo.config.annotation.DubboService;
import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

/**
 * @author hitopei
 * account service impl
 */
@DubboService
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountFreezeMapper accountFreezeMapper;
    @Autowired
    private AccountUsdMapper accountUsdMapper;
    @Autowired
    private AccountRmbMapper accountRmbMapper;
    @Override
    public Boolean transfer(BigDecimal amount) {
        return null;
    }

    @Override
    @HmilyTCC(confirmMethod = "confirmUsd", cancelMethod = "cancelUsd")
    public Boolean consumeUsd(BigDecimal amount, String fromUserId, String toUserId) {
        //减少美元账户的金额
        accountUsdMapper.decreaseUsd(fromUserId, amount);
        //增加冻结表的美元金额
        accountFreezeMapper.addFreeze(amount, fromUserId);
        System.out.println("调用b完成");
        return Boolean.TRUE;
    }

    public Boolean confirmUsd(BigDecimal amount, String fromUserId, String toUserId) {
        //减掉冻结表的金额
        accountFreezeMapper.unfreeze(amount, fromUserId);
        System.out.println("解冻完成");
        //add rmb
        BigDecimal rmb = amount.multiply(BigDecimal.valueOf(7));
        accountRmbMapper.addRmb(rmb, fromUserId);
        System.out.println("confirm done");
        return Boolean.TRUE;
    }

    public Boolean cancelUsd(BigDecimal amount, String fromUserId, String toUserId) {
        //减掉冻结表金额
        accountFreezeMapper.unfreeze(amount, fromUserId);
        //恢复美元账户
        accountUsdMapper.cancel(fromUserId, amount);
        System.out.println("cancel done");
        return Boolean.TRUE;
    }
}
