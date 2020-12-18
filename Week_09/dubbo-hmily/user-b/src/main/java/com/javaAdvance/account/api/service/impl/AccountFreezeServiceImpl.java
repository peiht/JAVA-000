package com.javaAdvance.account.api.service.impl;

import com.javaAdvance.account.api.repository.mysql.domain.AccountFreeze;
import com.javaAdvance.account.api.repository.mysql.mapper.AccountFreezeMapper;
import com.javaAdvance.account.api.service.AccountFreezeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hitopei
 * @since 2020-12-16
 */
@Service
public class AccountFreezeServiceImpl extends ServiceImpl<AccountFreezeMapper, AccountFreeze> implements AccountFreezeService {

}
