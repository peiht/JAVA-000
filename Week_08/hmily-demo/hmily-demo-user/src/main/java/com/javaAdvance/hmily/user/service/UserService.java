package com.javaAdvance.hmily.user.service;

import com.alibaba.fastjson.JSONObject;
import com.javaAdvance.hmily.user.base.ResultBean;
import com.javaAdvance.hmily.user.repository.mysql.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hitopei
 * @since 2020-12-09
 */
public interface UserService extends IService<User> {

    Boolean payAccount(JSONObject data);
}
