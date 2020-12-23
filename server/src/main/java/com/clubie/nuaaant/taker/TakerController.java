package com.clubie.nuaaant.taker;

import com.clubie.nuaaant.giver.GiverService;
import com.clubie.nuaaant.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TakerController {
    @Autowired
    private UserService userService;

    @Autowired
    private TakerService takerService;

    @GetMapping("/taker/giveup")
    public void GiveUp(String sessionID, int orderID) {
        var userID = userService.Session2ID(sessionID);
        takerService.GiveUp(userID, orderID);
    }

    @GetMapping("/taker/submit")
    public void Submit(String sessionID, int orderID) {
        var userID = userService.Session2ID(sessionID);
        takerService.Submit(userID, orderID);
    }

    @GetMapping("/taker/submit/cancel")
    public void CancelSubmit(String sessionID, int orderID) {
        var userID = userService.Session2ID(sessionID);
        takerService.CancelSubmit(userID, orderID);
    }
}
