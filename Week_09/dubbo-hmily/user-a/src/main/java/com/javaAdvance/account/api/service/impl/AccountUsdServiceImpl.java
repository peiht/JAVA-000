package com.javaAdvance.account.api.service.impl;

import com.javaAdvance.account.api.repository.mysql.domain.AccountUsd;
import com.javaAdvance.account.api.repository.mysql.mapper.AccountUsdMapper;
import com.javaAdvance.account.api.service.AccountUsdService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hitopei
 * @since 2020-12-16
 */
@Service
public class AccountUsdServiceImpl extends ServiceImpl<AccountUsdMapper, AccountUsd> implements AccountUsdService {

}
