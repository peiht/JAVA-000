package com.javaAdvance.sharding.service;

import com.javaAdvance.sharding.base.ResultBean;
import com.javaAdvance.sharding.repository.mysql.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hitopei
 * @since 2020-12-02
 */
public interface UserService extends IService<User> {

    ResultBean getUser(Integer id);

    ResultBean addUser(User user);
}
