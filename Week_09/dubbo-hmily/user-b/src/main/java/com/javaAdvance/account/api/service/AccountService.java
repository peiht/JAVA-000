package com.javaAdvance.account.api.service;

import org.dromara.hmily.annotation.Hmily;

import java.math.BigDecimal;

/**
 *
 * @author hitopeis
 * account interface
 */
public interface AccountService {

    /**
     * transfer amount
     * @param amount the coin
     * @return Boolean
     */
    Boolean transfer(BigDecimal amount);

    @Hmily
    Boolean consumeUsd(BigDecimal amount, String fromUserId, String toUserId);
}
