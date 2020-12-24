package com.clubie.nuaaant.order;

import com.clubie.nuaaant.apply.ApplyService;
import com.clubie.nuaaant.like.LikeService;
import com.clubie.nuaaant.remark.RemarkService;
import com.clubie.nuaaant.user.UserService;
import com.clubie.nuaaant.utils.NuaaAntException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    @Autowired
    private RemarkService remarkService;

    @Override
    public void AddOrder(int userID, Order order) {
        orderMapper.AddOrder(userID, order, new Date());
    }

    @Override
    public List<Map<String, Object>> GetOrderList(int userID, String searchText, int typeIndex, int sortIndex, int filterID) {
        var res = orderMapper.GetOrderList(userID, "%" + searchText + "%", typeIndex);
        if (res == null)
            throw new NuaaAntException("无法获取需求列表");
        res.sort((o1, o2) -> {
            switch (sortIndex) {
                case 0: {
                    var left = (Date) o1.get("PublishTime");
                    var right = (Date) o2.get("PublishTime");
                    return right.compareTo(left);
                }
                case 1: {
                    var left = (BigDecimal) o1.get("Reward");
                    var right = (BigDecimal) o2.get("Reward");
                    return right.compareTo(left);
                }
                case 2: {
                    var left = (Date) o1.get("Deadline");
                    var right = (Date) o2.get("Deadline");
                    if (left == null) {
                        if (right == null)
                            return 0;
                        else
                            return 1;
                    } else {
                        if (right == null)
                            return -1;
                        else
                            return left.compareTo(right);
                    }
                }
                case 3: {
                    var left = (long) o1.get("TakerCount") * 3 + (long) o1.get("LikeCount") + (long) o1.get("RemarkCount") * 2;
                    var right = (long) o2.get("TakerCount") * 3 + (long) o2.get("LikeCount") + (long) o2.get("RemarkCount") * 2;
                    return Long.compare(right, left);
                }
                case 4: {
                    var left = (BigDecimal) o1.get("GiverScore");
                    var right = (BigDecimal) o2.get("GiverScore");
                    return right.compareTo(left);
                }
                default:
                    throw new NuaaAntException("排序方式无效");
            }
        });
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
            var score = userService.GetUserScores(giverID);
            res.put("GiverScore", score.get("GiverScore"));
        }
        var takerID = (Integer) res.get("TakerID");
        if (takerID != null && takerID != 0) {
            var taker = userService.GetBasicInfo(takerID);
            res.put("TakerAvatarUrl", taker.get("AvatarUrl"));
            res.put("TakerName", taker.get("NickName"));
            var score = userService.GetUserScores(takerID);
            res.put("TakerScore", score.get("TakerScore"));
        }
        var appliers = applyService.GetAppliers(orderID);
        res.put("Appliers", appliers);
        res.put("Like", likeService.CheckLike(userID, orderID));
        res.put("LikeCount", likeService.GetLikeCount(orderID));
        var remarks = remarkService.GetRemarks(userID, orderID);
        res.put("Remarks", remarks);
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

    @Override
    public void EditOrder(int userID, int orderID, Order order) {
        var giverID = GetGiverID(orderID);
        if (userID != giverID)
            throw new NuaaAntException("无权限");
        orderMapper.EditOrder(orderID, order);
    }

    @Override
    public List<Map<String, Object>> GetUserOrderList(int userID, String searchText, int typeIndex, int sortIndex, int filterID) {
        var res = orderMapper.GetUserOrderList(userID, "%" + searchText + "%", typeIndex);
        if (res == null)
            throw new NuaaAntException("无法获取需求列表");
        res.sort((o1, o2) -> {
            switch (sortIndex) {
                case 0: {
                    var left = (Date) o1.get("PublishTime");
                    var right = (Date) o2.get("PublishTime");
                    return right.compareTo(left);
                }
                case 1: {
                    var left = (BigDecimal) o1.get("Reward");
                    var right = (BigDecimal) o2.get("Reward");
                    return right.compareTo(left);
                }
                case 2: {
                    var left = (Date) o1.get("Deadline");
                    var right = (Date) o2.get("Deadline");
                    if (left == null) {
                        if (right == null)
                            return 0;
                        else
                            return 1;
                    } else {
                        if (right == null)
                            return -1;
                        else
                            return left.compareTo(right);
                    }
                }
                case 3: {
                    var left = (long) o1.get("TakerCount") * 3 + (long) o1.get("LikeCount") + (long) o1.get("RemarkCount") * 2;
                    var right = (long) o2.get("TakerCount") * 3 + (long) o2.get("LikeCount") + (long) o2.get("RemarkCount") * 2;
                    return Long.compare(right, left);
                }
                default:
                    throw new NuaaAntException("排序方式无效");
            }
        });
        return res;
    }
}
