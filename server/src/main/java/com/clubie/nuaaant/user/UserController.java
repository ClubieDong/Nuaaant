package com.clubie.nuaaant.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/login")
    public Map<String, Object> Login(String code) {
        return userService.Login(code);
    }

    @GetMapping("/user/get")
    public User GetUserInfo(String sessionID) {
        return userService.GetUserInfo(sessionID);
    }

    @PostMapping("/user/set")
    public void SetUserInfo(String sessionID, User user) {
        userService.SetUserInfo(sessionID, user);
    }

}
