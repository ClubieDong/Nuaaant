package com.clubie.nuaaant.message;

import java.util.List;
import java.util.Map;

public interface MessageService {

    void AddMessage(int senderID, int receiverID, String text);

    List<Map<String, Object>> GetMessage(int senderID, int receiverID);

    void ReadAll(int senderID, int receiverID);

    List<Map<String, Object>> GetMessageList(int userID);
}
