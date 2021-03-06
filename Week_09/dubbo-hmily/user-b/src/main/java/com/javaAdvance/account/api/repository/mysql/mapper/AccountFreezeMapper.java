package com.javaAdvance.account.api.repository.mysql.mapper;

import com.javaAdvance.account.api.repository.mysql.domain.AccountFreeze;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hitopei
 * @since 2020-12-16
 */
@Repository
public interface AccountFreezeMapper extends BaseMapper<AccountFreeze> {

    int addFreeze(BigDecimal amount, String userId);

    int unfreeze(BigDecimal amount, String userId);
}
