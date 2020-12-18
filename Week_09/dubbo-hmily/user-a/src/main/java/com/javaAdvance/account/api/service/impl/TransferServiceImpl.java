package com.javaAdvance.account.api.service.impl;

import com.javaAdvance.account.api.repository.mysql.mapper.AccountFreezeMapper;
import com.javaAdvance.account.api.repository.mysql.mapper.AccountRmbMapper;
import com.javaAdvance.account.api.repository.mysql.mapper.AccountUsdMapper;
import com.javaAdvance.account.api.service.AccountService;
import com.javaAdvance.account.api.service.TransferService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class TransferServiceImpl implements TransferService {

    @Autowired
    private AccountRmbMapper accountRmbMapper;
    @Autowired
    private AccountUsdMapper accountUsdMapper;
    @Autowired
    private AccountFreezeMapper accountFreezeMapper;
    @DubboReference
    private AccountService accountService;

    @Override
    @HmilyTCC(confirmMethod = "confirmRmb", cancelMethod = "cancelRmb")
    public Boolean transferRmb(BigDecimal amount, String fromUserId, String toUserId) {
        //减去当前余额RMB
        int updateRes = accountRmbMapper.update(fromUserId, amount);
        //增加冻结表的RMB
        int freezeUpdate = accountFreezeMapper.updateFreezeRMB(fromUserId, amount);
        return Boolean.TRUE;
    }

    public Boolean confirmRmb(BigDecimal amount, String fromUserId, String toUserId) {
        //解冻RMB
        int unFreeze = accountFreezeMapper.unFreezeRMB(fromUserId, amount);
        //dubbo 发送RMB请求到b
        BigDecimal usd = amount.divide(BigDecimal.valueOf(7), RoundingMode.HALF_DOWN);
        //dubbo调用
        accountService.consumeUsd(usd, toUserId, fromUserId);
        System.out.println("调用b完成");
        accountUsdMapper.updateUsd(usd, fromUserId);
        System.out.println("confirm done");
        return Boolean.TRUE;
    }

    public Boolean cancelRmb(BigDecimal amount, String fromUserId, String toUserId) {
        //RMB回归
        int accountRes = accountRmbMapper.cancel(fromUserId, amount);
        //解冻RMB
        int unFreeze = accountFreezeMapper.unFreezeRMB(fromUserId, amount);
        return Boolean.TRUE;
    }
}
