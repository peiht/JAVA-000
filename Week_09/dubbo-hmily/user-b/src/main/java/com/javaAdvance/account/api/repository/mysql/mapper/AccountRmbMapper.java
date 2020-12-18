package com.javaAdvance.account.api.repository.mysql.mapper;

import com.javaAdvance.account.api.repository.mysql.domain.AccountRmb;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hitopei
 * @since 2020-12-16
 */
public interface AccountRmbMapper extends BaseMapper<AccountRmb> {

    int addRmb(@Param("amount") BigDecimal amount, @Param("userId") String userId);
}
