package com.javaAdvance.account.api.web;

import com.javaAdvance.account.api.service.AccountService;
import com.javaAdvance.account.api.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @author hitopei
 *
 * account api
 */
@RestController
@RequestMapping("account")
public class AccountController {

    @Autowired
    private TransferService transferService;

    /**
     * 兑换人民币
     * @param amount
     * @param fromUserId
     * @param toUserId
     * @return
     */
    @RequestMapping("transferRMB")
    public Boolean transferRmb(BigDecimal amount, String fromUserId, String toUserId){
        return transferService.transferRmb(amount, fromUserId, toUserId);
    }
}
