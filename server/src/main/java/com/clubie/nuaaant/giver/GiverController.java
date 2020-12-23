package com.clubie.nuaaant.giver;

import com.clubie.nuaaant.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GiverController {
    @Autowired
    private UserService userService;

    @Autowired
    private GiverService giverService;

    @GetMapping("/giver/withdraw")
    public void Withdraw(String sessionID, int orderID) {
        var userID = userService.Session2ID(sessionID);
        giverService.Withdraw(userID, orderID);
    }

    @GetMapping("/giver/republish")
    public void Republish(String sessionID, int orderID) {
        var userID = userService.Session2ID(sessionID);
        giverService.Republish(userID, orderID);
    }

    @GetMapping("/giver/choose")
    public void ChooseTaker(String sessionID, int orderID, int takerID) {
        var userID = userService.Session2ID(sessionID);
        giverService.ChooseTaker(userID, orderID, takerID);
    }

    @GetMapping("/giver/choose/cancel")
    public void CancelChooseTaker(String sessionID, int orderID) {
        var userID = userService.Session2ID(sessionID);
        giverService.CancelChooseTaker(userID, orderID);
    }

    @GetMapping("/giver/accept")
    public void Accept(String sessionID, int orderID) {
        var userID = userService.Session2ID(sessionID);
        giverService.Accept(userID, orderID);
    }

    @GetMapping("/giver/reject")
    public void Reject(String sessionID, int orderID) {
        var userID = userService.Session2ID(sessionID);
        giverService.Reject(userID, orderID);
    }
}
