package com.javaAdvance.sharding.service.impl;

import com.javaAdvance.sharding.base.ResultBean;
import com.javaAdvance.sharding.repository.mysql.domain.User;
import com.javaAdvance.sharding.repository.mysql.mapper.UserMapper;
import com.javaAdvance.sharding.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shardingsphere.api.hint.HintManager;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hitopei
 * @since 2020-12-02
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public ResultBean getUser(Integer id) {
        HintManager hintManager = HintManager.getInstance();
        hintManager.setMasterRouteOnly();
        User user = this.getById(id);
        hintManager.close();
        return ResultBean.success(user);
    }

    @Override
    public ResultBean addUser(User user) {
        user.setcTime(new Date());
        this.save(user);
        return ResultBean.success();
    }
}
