package com.javaAdvance.account.api.repository.mysql.mapper;

import com.javaAdvance.account.api.repository.mysql.domain.AccountRmb;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
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
public interface AccountRmbMapper extends BaseMapper<AccountRmb> {

    int update(@Param("userId") String userId, @Param("amount") BigDecimal amount);

    int cancel(@Param("userId") String userId, @Param("amount") BigDecimal amount);
}
