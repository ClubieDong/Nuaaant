package com.clubie.nuaaant.order;

import com.clubie.nuaaant.apply.ApplyService;
import com.clubie.nuaaant.like.LikeService;
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

    @Autowired
    private ApplyService applyService;

    @Autowired
    private LikeService likeService;

    @Override
    public void AddOrder(int userID, Order order) {
        orderMapper.AddOrder(userID, order, new Date());
    }

    @Override
    public List<Map<String, Object>> GetOrderList(int userID, String searchText, int typeIndex, int sortIndex, int filterID) {
        // TODO
        var res = orderMapper.GetOrderList();
        if (res == null)
            throw new NuaaAntException("无法获取需求列表");
        return res;
    }

    @Override
    public Map<String, Object> GetOrderDetail(int userID, int orderID) {
        var res = orderMapper.GetOrder(orderID);
        if (res == null)
            throw new NuaaAntException("找不到需求");
        var giverID = (Integer) res.get("GiverID");
        if (giverID != null && giverID != 0) {
            var giver = userService.GetBasicInfo(giverID);
            res.put("GiverAvatarUrl", giver.get("AvatarUrl"));
            res.put("GiverName", giver.get("NickName"));
            res.put("GiverScore", giver.get("Score"));
        }
        var takerID = (Integer) res.get("TakerID");
        if (takerID != null && takerID != 0) {
            var taker = userService.GetBasicInfo(takerID);
            res.put("TakerAvatarUrl", taker.get("AvatarUrl"));
            res.put("TakerName", taker.get("NickName"));
            res.put("TakerScore", taker.get("Score"));
        }
        var appliers = applyService.GetAppliers(orderID);
        res.put("Appliers", appliers);
        res.put("Like", likeService.CheckLike(userID, orderID));
        res.put("LikeCount", likeService.GetLikeCount(orderID));
        return res;
    }

    @Override
    public int GetState(int orderID) {
        var state = orderMapper.GetState(orderID);
        if (state == null)
            throw new NuaaAntException("找不到需求");
        return state;
    }

    @Override
    public boolean CheckExist(int orderID) {
        return orderMapper.CheckExist(orderID) == 1;
    }

    @Override
    public int GetGiverID(int orderID) {
        var res = orderMapper.GetGiver(orderID);
        if (res == null)
            throw new NuaaAntException("找不到模板/需求");
        return res;
    }

    @Override
    public int GetTakerID(int orderID) {
        var res = orderMapper.GetTaker(orderID);
        if (res == null)
            throw new NuaaAntException("无接单者或找不到模板/需求");
        return res;
    }
}
