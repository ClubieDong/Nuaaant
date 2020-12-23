package com.clubie.nuaaant.giver;

import com.clubie.nuaaant.apply.ApplyService;
import com.clubie.nuaaant.order.OrderService;
import com.clubie.nuaaant.utils.NuaaAntException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class GiverServiceImpl implements GiverService {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ApplyService applyService;
    @Autowired
    private GiverMapper giverMapper;

    @Override
    public void Republish(int userID, int orderID) {
        var giverID = orderService.GetGiverID(orderID);
        if (userID != giverID)
            throw new NuaaAntException("无权限");
        var state = orderService.GetState(orderID);
        if (state != 0)
            throw new NuaaAntException("无法重新发布");
        giverMapper.Republish(orderID, new Date());
    }

    @Override
    public void Withdraw(int userID, int orderID) {
        var giverID = orderService.GetGiverID(orderID);
        if (userID != giverID)
            throw new NuaaAntException("无权限");
        var state = orderService.GetState(orderID);
        if (state != 1)
            throw new NuaaAntException("无法撤回");
        giverMapper.Withdraw(orderID);
        applyService.DeleteAllAppliers(orderID);
    }

    @Override
    public void ChooseTaker(int userID, int orderID, int takerID) {
        var giverID = orderService.GetGiverID(orderID);
        if (userID != giverID)
            throw new NuaaAntException("无权限");
        var state = orderService.GetState(orderID);
        if (state != 1)
            throw new NuaaAntException("无法选择接单者");
        if (!applyService.IsApplier(orderID, takerID))
            throw new NuaaAntException("该用户未申请");
        giverMapper.ChooseTaker(orderID, takerID, new Date());
    }

    @Override
    public void CancelChooseTaker(int userID, int orderID) {
        var giverID = orderService.GetGiverID(orderID);
        if (userID != giverID)
            throw new NuaaAntException("无权限");
        var state = orderService.GetState(orderID);
        if (state != 2)
            throw new NuaaAntException("无法取消接单者");
        giverMapper.CancelChooseTaker(orderID);
    }

    @Override
    public void Accept(int userID, int orderID) {
        var giverID = orderService.GetGiverID(orderID);
        if (userID != giverID)
            throw new NuaaAntException("无权限");
        var state = orderService.GetState(orderID);
        if (state != 3)
            throw new NuaaAntException("无法确认");
        giverMapper.Accept(orderID, new Date());
    }

    @Override
    public void Reject(int userID, int orderID) {
        var giverID = orderService.GetGiverID(orderID);
        if (userID != giverID)
            throw new NuaaAntException("无权限");
        var state = orderService.GetState(orderID);
        if (state != 3)
            throw new NuaaAntException("无法驳回");
        giverMapper.Reject(orderID);
    }
}
