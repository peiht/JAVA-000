package com.javaAdvance.account.api.service;

import org.dromara.hmily.annotation.Hmily;

import java.math.BigDecimal;

/**
 *
 * @author hitopeis
 * account interface
 */
public interface AccountService {

    @Hmily
    Boolean consumeUsd(BigDecimal amount, String fromUserId, String toUserId);
}
