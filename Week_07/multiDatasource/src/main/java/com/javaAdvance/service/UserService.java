package com.javaAdvance.service;

import com.javaAdvance.repository.mysql.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hitopei
 * @since 2020-12-02
 */
public interface UserService extends IService<User> {

    /**
     * 添加用户
     *
     * @return
     */
    boolean addUser(User user);

    /**
     * 展示所有的user
     * @return
     */
    List<User> listUsers();

    /**
     * user detail
     * @param id
     * @return
     */
    User userDetail(int id);

    /**
     * 删除
     * @param id
     * @return
     */
    boolean delUser(int id);

    /**
     * 修改
     * @param user
     * @return
     */
    boolean updateUser(User user);
}
