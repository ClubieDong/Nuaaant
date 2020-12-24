package com.clubie.nuaaant.order;

import com.clubie.nuaaant.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class OrderController {
    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @PostMapping("/order/add")
    public void AddOrder(String sessionID, Order order) {
        var userID = userService.Session2ID(sessionID);
        orderService.AddOrder(userID, order);
    }

    @GetMapping("/order/list")
    public List<Map<String, Object>> GetOrderList(String sessionID, String searchText,
                                                  int typeIndex, int sortIndex, int filterID) {
        var userID = userService.Session2ID(sessionID);
        return orderService.GetOrderList(userID, searchText, typeIndex, sortIndex, filterID);
    }

    @GetMapping("/order/detail")
    public Map<String, Object> GetOrderDetail(String sessionID, int orderID) {
        var userID = userService.Session2ID(sessionID);
        return orderService.GetOrderDetail(userID, orderID);
    }

    @PostMapping("/order/edit")
    public void EditOrder(String sessionID, int orderID, Order order) {
        var userID = userService.Session2ID(sessionID);
        orderService.EditOrder(userID, orderID, order);
    }

    @GetMapping("/order/list/user")
    public List<Map<String, Object>> GetUserOrderList(String sessionID, int userID, String searchText,
                                                      int typeIndex, int sortIndex, int filterID) {
        userService.Session2ID(sessionID);
        return orderService.GetUserOrderList(userID, searchText, typeIndex, sortIndex, filterID);
    }
}
