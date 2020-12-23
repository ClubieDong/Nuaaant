package com.clubie.nuaaant.template;

import com.clubie.nuaaant.order.Order;
import com.clubie.nuaaant.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class TemplateController {
    @Autowired
    private UserService userService;

    @Autowired
    private TemplateService templateService;

    @GetMapping("/template/list")
    public List<Map<String, Object>> GetTemplateList(String sessionID) {
        var userID = userService.Session2ID(sessionID);
        return templateService.GetTemplateList(userID);
    }

    @PostMapping("/template/add")
    public void AddTemplate(String sessionID, Template template) {
        var userID = userService.Session2ID(sessionID);
        templateService.AddTemplate(userID, template);
    }

    @GetMapping("/template/get")
    public Template GetTemplate(String sessionID, int templateID) {
        userService.Session2ID(sessionID);
        return templateService.GetTemplate(templateID);
    }

    @DeleteMapping("/template/delete")
    public void DeleteTemplate(String sessionID, int templateID) {
        var userID = userService.Session2ID(sessionID);
        templateService.DeleteTemplate(userID, templateID);
    }

}
