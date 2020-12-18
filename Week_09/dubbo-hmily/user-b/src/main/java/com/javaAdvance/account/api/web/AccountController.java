package com.javaAdvance.account.api.web;

import com.javaAdvance.account.api.service.AccountService;
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
    private AccountService accountService;

    @RequestMapping("transfer")
    public Boolean transfer(BigDecimal amount, String fromUserId, String toUserId){
        return accountService.consumeUsd(amount, fromUserId, toUserId);
    }
}
