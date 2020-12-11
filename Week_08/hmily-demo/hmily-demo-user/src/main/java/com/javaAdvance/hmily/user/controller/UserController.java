package com.javaAdvance.hmily.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.javaAdvance.hmily.user.base.ResultBean;
import com.javaAdvance.hmily.user.repository.mysql.domain.User;
import com.javaAdvance.hmily.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ty
 *
 * user
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     *
     * @param userId 用户id
     * @param amount 计算之后需要扣除的金额
     * @return
     */
    @RequestMapping("dealAmount")
    public ResultBean dealAmount(@RequestParam("userId") Integer userId,
                                 @RequestParam("amount") Integer amount){
        return ResultBean.success();
    }

    /**
     * 新增用户测试s
     * @param user
     * @return
     */
    @RequestMapping("addUser")
    public ResultBean addUser(@RequestBody User user){
        if (userService.save(user)) {
            return ResultBean.success();
        }
        return ResultBean.error();
    }

    @RequestMapping("pay")
    public Boolean pay(@RequestBody JSONObject data){
        return userService.payAccount(data);
    }
}
