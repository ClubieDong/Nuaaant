package com.clubie.nuaaant.order;

import com.clubie.nuaaant.user.UserService;
import com.clubie.nuaaant.utils.NuaaAntException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserService userService;

    @Override
    public List<Map<String, Object>> GetTemplateList(String sessionID) {
        var userID = userService.GetUserIDBySessionID(sessionID);
        return orderMapper.GetTemplateList(userID);
    }

    @Override
    public void AddTemplate(String sessionID, Order order) {
        var userID = userService.GetUserIDBySessionID(sessionID);
        order.setIsTemplate(true);
        order.setGiverID(userID);
        orderMapper.AddOrder(order);
    }

    @Override
    public void CheckGiver(String sessionID, int id) {
        var userID = userService.GetUserIDBySessionID(sessionID);
        var giverID = orderMapper.GetGiverByID(id);
        if (giverID != null && userID != giverID)
            throw new NuaaAntException("无权限");
    }

    @Override
    public Order GetTemplateByID(String sessionID, int id) {
        CheckGiver(sessionID, id);
        var res = orderMapper.GetOrderByID(id);
        if (res == null)
            throw new NuaaAntException("找不到模板");
        return res;
    }

    @Override
    public void DeleteTemplateByID(String sessionID, int id) {
        CheckGiver(sessionID, id);
        orderMapper.DeleteOrderByID(id);
    }

    @Override
    public void AddOrder(String sessionID, Order order) {
        var userID = userService.GetUserIDBySessionID(sessionID);
        order.setIsTemplate(false);
        order.setGiverID(userID);
        orderMapper.AddOrder(order);
    }

    // TypeIndex
    // Deadline
    // Title
    // Reward
    // AvatarUrl - join
    // GiverName - join
    // GiverScore - join
    // TakerCount - count
    // LikeCount
    // RemarkCount

    @Override
    public List<Map<String, Object>> GetOrderList(String sessionID, String searchText, int typeIndex, int sortIndex, int filterID) {
        return orderMapper.GetOrderList();
    }
}
