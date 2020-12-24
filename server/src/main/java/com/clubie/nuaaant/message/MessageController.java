package com.clubie.nuaaant.message;

import com.clubie.nuaaant.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class MessageController {
    @Autowired
    private UserService userService;
    @Autowired
    private MessageService messageService;

    @PostMapping("/message/add")
    public void AddMessage(String sessionID, int receiverID, String text) {
        var senderID = userService.Session2ID(sessionID);
        messageService.AddMessage(senderID, receiverID, text);
    }

    @GetMapping("/message/get")
    public List<Map<String, Object>> GetMessage(String sessionID, int userID) {
        var anotherUserID = userService.Session2ID(sessionID);
        return messageService.GetMessage(userID, anotherUserID);
    }

    @GetMapping("/message/list")
    public List<Map<String, Object>> GetMessageList(String sessionID) {
        var userID = userService.Session2ID(sessionID);
        return messageService.GetMessageList(userID);
    }
}
