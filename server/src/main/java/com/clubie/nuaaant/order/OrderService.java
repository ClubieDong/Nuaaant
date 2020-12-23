package com.clubie.nuaaant.order;

import java.util.List;
import java.util.Map;

public interface OrderService {
    void AddOrder(int userID, Order order);

    List<Map<String, Object>> GetOrderList(int userID, String searchText, int typeIndex, int sortIndex, int filterID);

    Map<String, Object> GetOrderDetail(int userID, int orderID);

    int GetState(int orderID);

    boolean CheckExist(int orderID);

    int GetGiverID(int orderID);

    int GetTakerID(int orderID);

    void EditOrder(int userID, int orderID, Order order);
}
