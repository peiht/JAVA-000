package com.javaAdvance.web;

import com.javaAdvance.base.ResCode;
import com.javaAdvance.base.ResultBean;
import com.javaAdvance.repository.mysql.domain.User;
import com.javaAdvance.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * user controller
 * @author hitopei
 */
@RestController
@RequestMapping(value = "user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(value = "listUser")
    public ResultBean listUser(){
        return ResultBean.success(userService.listUsers());
    }

    @GetMapping(value = "userDetail")
    public ResultBean userDetail(@RequestParam Integer id) {
        return ResultBean.success(userService.userDetail(id));
    }

    @PostMapping(value = "addUser")
    public ResultBean addUser(@RequestBody User user) {
        userService.addUser(user);
        return ResultBean.success(); }

    @DeleteMapping(value = "delUser")
    public ResultBean delUser(@RequestParam Integer id) {
        userService.delUser(id);
        return ResultBean.success();
    }

    @PutMapping(value = "updateUser")
    public ResultBean updateUser(@RequestBody User user){
        userService.updateUser(user);
        return ResultBean.success();
    }


}
