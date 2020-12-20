package com.clubie.nuaaant.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/order/template/list")
    public List<Map<String, Object>> GetTemplateList(String sessionID) {
        return orderService.GetTemplateList(sessionID);
    }

    @PostMapping("/order/template")
    public void AddTemplate(String sessionID, Order order) {
        orderService.AddTemplate(sessionID, order);
    }

    @GetMapping("/order/template")
    public Order GetTemplateByID(String sessionID, int id) {
        return orderService.GetTemplateByID(sessionID, id);
    }

    @DeleteMapping("/order/template")
    public void DeleteTemplateByID(String sessionID, int id) {
        orderService.DeleteTemplateByID(sessionID, id);
    }

    @PostMapping("/order")
    public void AddOrder(String sessionID, Order order) {
        orderService.AddOrder(sessionID, order);
    }

}
