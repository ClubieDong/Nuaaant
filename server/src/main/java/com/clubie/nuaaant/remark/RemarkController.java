package com.clubie.nuaaant.remark;

import com.clubie.nuaaant.order.Order;
import com.clubie.nuaaant.order.OrderService;
import com.clubie.nuaaant.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class RemarkController {
    @Autowired
    private UserService userService;

    @Autowired
    private RemarkService remarkService;

    @PostMapping("/remark/add")
    public void AddRemark(String sessionID, int orderID, String text) {
        var userID = userService.Session2ID(sessionID);
        remarkService.AddRemark(userID, orderID, text);
    }
}
