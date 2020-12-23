package com.clubie.nuaaant.taker;

import com.clubie.nuaaant.apply.ApplyService;
import com.clubie.nuaaant.order.OrderService;
import com.clubie.nuaaant.utils.NuaaAntException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TakerServiceImpl implements TakerService {
    @Autowired
    private OrderService orderService;
    @Autowired
    private TakerMapper takerMapper;
    @Autowired
    private ApplyService applyService;

    @Override
    public void GiveUp(int userID, int orderID) {
        var takerID = orderService.GetTakerID(orderID);
        if (userID != takerID)
            throw new NuaaAntException("无权限");
        var state = orderService.GetState(orderID);
        if (state != 2)
            throw new NuaaAntException("无法放弃接单");
        takerMapper.GiveUp(orderID);
        applyService.CancelApply(userID, orderID);
    }

    @Override
    public void Submit(int userID, int orderID) {
        var takerID = orderService.GetTakerID(orderID);
        if (userID != takerID)
            throw new NuaaAntException("无权限");
        var state = orderService.GetState(orderID);
        if (state != 2)
            throw new NuaaAntException("无法提交结果");
        takerMapper.Submit(orderID, new Date());
    }

    @Override
    public void CancelSubmit(int userID, int orderID) {
        var takerID = orderService.GetTakerID(orderID);
        if (userID != takerID)
            throw new NuaaAntException("无权限");
        var state = orderService.GetState(orderID);
        if (state != 3)
            throw new NuaaAntException("无法撤回提交");
        takerMapper.CancelSubmit(orderID);
    }
}
