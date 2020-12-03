package com.javaAdvance.sharding.web;


import com.javaAdvance.sharding.base.ResultBean;
import com.javaAdvance.sharding.repository.mysql.domain.User;
import com.javaAdvance.sharding.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("addUser")
    public ResultBean addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @GetMapping("getUser")
    public ResultBean getUser(@RequestParam Integer id){
        return userService.getUser(id);
    }
}
