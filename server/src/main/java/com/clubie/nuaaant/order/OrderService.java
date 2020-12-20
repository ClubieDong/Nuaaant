package com.clubie.nuaaant.order;

import java.util.List;
import java.util.Map;

public interface OrderService {
    List<Map<String, Object>> GetTemplateList(String sessionID);

    void AddTemplate(String sessionID, Order order);

    void CheckGiver(String sessionID, int id);

    Order GetTemplateByID(String sessionID, int id);

    void DeleteTemplateByID(String sessionID, int id);

    void AddOrder(String sessionID, Order order);
}
