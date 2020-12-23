package com.clubie.nuaaant.like;

import com.clubie.nuaaant.order.OrderService;
import com.clubie.nuaaant.utils.NuaaAntException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements LikeService {
    @Autowired
    private LikeMapper likeMapper;

    @Autowired
    private OrderService orderService;

    @Override
    public void Like(int userID, int orderID) {
        if (!orderService.CheckExist(orderID))
            throw new NuaaAntException("找不到需求");
        try {
            likeMapper.Like(userID, orderID);
        } catch (Exception e) {
            throw new NuaaAntException("无法关注");
        }
    }

    @Override
    public void CancelLike(int userID, int orderID) {
        if (!orderService.CheckExist(orderID))
            throw new NuaaAntException("找不到需求");
        likeMapper.CancelLike(userID, orderID);
    }

    @Override
    public boolean CheckLike(int userID, int orderID) {
        return likeMapper.CheckLike(userID, orderID) == 1;
    }

    @Override
    public int GetLikeCount(int orderID) {
        return likeMapper.GetLikeCount(orderID);
    }
}
