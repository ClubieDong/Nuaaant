package com.clubie.nuaaant.user;

import com.clubie.nuaaant.utils.ReturnWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public Map<String, Object> Login(String code) {
        try {
            var res = userService.Login(code);
            return ReturnWrapper.Success(res);
        } catch (Exception e) {
            return ReturnWrapper.Fail(e);
        }
    }

}
