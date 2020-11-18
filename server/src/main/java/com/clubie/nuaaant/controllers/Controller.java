package com.clubie.nuaaant.controllers;

import com.clubie.nuaaant.entities.User;
import com.clubie.nuaaant.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/hello")
    public String index() {
        User user = userMapper.findByName("Clubie");
        return user.toString();
    }

}
