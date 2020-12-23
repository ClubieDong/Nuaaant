package com.clubie.nuaaant.like;

import com.clubie.nuaaant.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LikeController {
    @Autowired
    private LikeService likeService;

    @Autowired
    private UserService userService;

    @GetMapping("/like")
    public void Like(String sessionID, int orderID) {
        var userID = userService.Session2ID(sessionID);
        likeService.Like(userID, orderID);
    }

    @GetMapping("/like/cancel")
    public void CancelLike(String sessionID, int orderID) {
        var userID = userService.Session2ID(sessionID);
        likeService.CancelLike(userID, orderID);
    }
}
