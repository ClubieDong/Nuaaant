package com.clubie.nuaaant.agree;

import com.clubie.nuaaant.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AgreeController {
    @Autowired
    private AgreeService agreeService;

    @Autowired
    private UserService userService;

    @GetMapping("/agree")
    public void Agree(String sessionID, int remarkID) {
        var userID = userService.Session2ID(sessionID);
        agreeService.Agree(userID, remarkID);
    }

    @GetMapping("/agree/cancel")
    public void CancelAgree(String sessionID, int remarkID) {
        var userID = userService.Session2ID(sessionID);
        agreeService.CancelAgree(userID, remarkID);
    }
}
