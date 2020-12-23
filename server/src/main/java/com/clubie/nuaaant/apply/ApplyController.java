package com.clubie.nuaaant.apply;

import com.clubie.nuaaant.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApplyController {
    @Autowired
    private ApplyService applyService;

    @Autowired
    private UserService userService;

    @GetMapping("/apply")
    public void Apply(String sessionID, int orderID) {
        var userID = userService.Session2ID(sessionID);
        applyService.Apply(userID, orderID);
    }

    @GetMapping("/apply/cancel")
    public void CancelApply(String sessionID, int orderID) {
        var userID = userService.Session2ID(sessionID);
        applyService.CancelApply(userID, orderID);
    }
}
