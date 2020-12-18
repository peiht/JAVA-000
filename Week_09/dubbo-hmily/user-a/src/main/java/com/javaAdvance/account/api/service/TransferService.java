package com.javaAdvance.account.api.service;

import java.math.BigDecimal;

public interface TransferService {
    /**
     * transfer from rmb to usd
     * @param amount the coin
     * @param fromUserId  source user id
     * @param toUserId target user id
     * @return Boolean
     */
    Boolean transferRmb(BigDecimal amount, String fromUserId, String toUserId);
}
