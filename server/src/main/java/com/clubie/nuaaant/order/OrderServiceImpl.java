package com.clubie.nuaaant.order;

import com.clubie.nuaaant.user.UserService;
import com.clubie.nuaaant.utils.NuaaAntException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
        var res = orderMapper.GetTemplateByID(id);
        if (res == null)
            throw new NuaaAntException("找不到模板");
        return res;
    }

    @Override
    public void DeleteTemplateByID(String sessionID, int id) {
        CheckGiver(sessionID, id);
        orderMapper.DeleteTemplateByID(id);
    }

    @Override
    public void AddOrder(String sessionID, Order order) {
        var userID = userService.GetUserIDBySessionID(sessionID);
        order.setIsTemplate(false);
        order.setGiverID(userID);
        order.setState(1);
        order.setPublishTime(new Date());
        orderMapper.AddOrder(order);
    }

    @Override
    public List<Map<String, Object>> GetOrderList(String sessionID, String searchText, int typeIndex, int sortIndex, int filterID) {
        return orderMapper.GetOrderList();
    }

    @Override
    public Map<String, Object> GetOrderByID(String sessionID, int orderID) {
        var userID = userService.GetUserIDBySessionID(sessionID);
        var res = orderMapper.GetOrderByID(orderID);
        if (res == null)
            throw new NuaaAntException("找不到需求");
        var giverID = (Integer) res.get("GiverID");
        if (giverID != null && giverID != 0) {
            var giver = userService.GetBasicInfoByID(giverID);
            res.put("GiverAvatarUrl", giver.get("AvatarUrl"));
            res.put("GiverName", giver.get("NickName"));
            res.put("GiverScore", giver.get("Score"));
        }
        var takerID = (Integer) res.get("TakerID");
        if (takerID != null && takerID != 0) {
            var taker = userService.GetBasicInfoByID(takerID);
            res.put("TakerAvatarUrl", taker.get("AvatarUrl"));
            res.put("TakerName", taker.get("NickName"));
            res.put("TakerScore", taker.get("Score"));
        }
        var appliers = orderMapper.GetApplierInfo(orderID);
        if (appliers != null)
            res.put("Appliers", appliers);
        res.put("Like", orderMapper.CheckLike(userID, orderID) == 1);
        res.put("LikeCount", orderMapper.GetLikeCount(orderID));
        return res;
    }

    @Override
    public void Apply(String sessionID, int orderID) {
        var userID = userService.GetUserIDBySessionID(sessionID);
        var orderState = orderMapper.GetOrderState(orderID);
        if (orderState == null)
            throw new NuaaAntException("找不到需求");
        if (orderState != 1)
            throw new NuaaAntException("无法申请");
        try {
            orderMapper.Apply(userID, orderID);
        } catch (Exception e) {
            throw new NuaaAntException("无法申请");
        }
    }

    @Override
    public void CancelApply(String sessionID, int orderID) {
        var userID = userService.GetUserIDBySessionID(sessionID);
        var orderState = orderMapper.GetOrderState(orderID);
        if (orderState == null)
            throw new NuaaAntException("找不到需求");
        if (orderState != 1)
            throw new NuaaAntException("无法取消申请");
        orderMapper.CancelApply(userID, orderID);
    }

    @Override
    public void Like(String sessionID, int orderID) {
        var userID = userService.GetUserIDBySessionID(sessionID);
        var orderState = orderMapper.GetOrderState(orderID);
        if (orderState == null)
            throw new NuaaAntException("找不到需求");
        try {
            orderMapper.Like(userID, orderID);
        } catch (Exception e) {
            throw new NuaaAntException("无法关注");
        }
    }

    @Override
    public void CancelLike(String sessionID, int orderID) {
        var userID = userService.GetUserIDBySessionID(sessionID);
        var orderState = orderMapper.GetOrderState(orderID);
        if (orderState == null)
            throw new NuaaAntException("找不到需求");
        orderMapper.CancelLike(userID, orderID);
    }
}
