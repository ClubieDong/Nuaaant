package com.clubie.nuaaant.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String Login(String code) {
        return userService.Login(code);
    }

    @GetMapping("/user")
    public User GetUserInfo(String sessionID) {
        return userService.GetUserInfo(sessionID);
    }

    @PostMapping("/user")
    public void SetUserInfo(String sessionID, User user) {
        userService.SetUserInfo(sessionID, user);
    }

}
