package com.lilacode.controller;

import com.lilacode.service.UserService;
import com.lilacode.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    @Resource
    private UserService userService;

    @PostMapping("/signup")
    public User signup(@RequestParam(value = "name", defaultValue = "-") String nickname) {
        return userService.createUser(nickname);
    }
    @GetMapping(value = "/allusers")
    public List<User> getEmployees() {
        return userService.getAllUsers();

    }

    @GetMapping
    public User getUser(@RequestParam(value = "id", defaultValue = "-1") int userId){
        return userService.getUser(userId);
    }

}