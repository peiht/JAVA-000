package com.javaAdvance.service.impl;

import com.javaAdvance.config.DataSourceType;
import com.javaAdvance.config.MyDataSource;
import com.javaAdvance.repository.mysql.domain.User;
import com.javaAdvance.repository.mysql.mapper.UserMapper;
import com.javaAdvance.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
    @MyDataSource(value = DataSourceType.Master)
    public boolean addUser(User user) {
        user.setcTime(new Date());
        user.setuTime(new Date());
        return this.save(user);
    }

    @Override
    @MyDataSource(value = DataSourceType.Slave)
    public List<User> listUsers() {
        return this.list();
    }

    @Override
    @MyDataSource(value = DataSourceType.Slave)
    public User userDetail(int id) {
        return this.getById(id);
    }

    @Override
    @MyDataSource(value = DataSourceType.Master)
    public boolean delUser(int id) {
        return this.removeById(id);
    }

    @Override
    @MyDataSource(value = DataSourceType.Master)
    public boolean updateUser(User user) {
        user.setuTime(new Date());
        return this.updateById(user);
    }
}
