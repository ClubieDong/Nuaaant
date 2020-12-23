package com.clubie.nuaaant.apply;

import com.clubie.nuaaant.order.OrderService;
import com.clubie.nuaaant.user.UserService;
import com.clubie.nuaaant.utils.NuaaAntException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ApplyServiceImpl implements ApplyService {

    @Autowired
    private ApplyMapper applyMapper;

    @Autowired
    private OrderService orderService;

    @Override
    public void Apply(int userID, int orderID) {
        if (orderService.GetState(orderID) != 1)
            throw new NuaaAntException("无法申请");
        try {
            applyMapper.Apply(userID, orderID);
        } catch (Exception e) {
            throw new NuaaAntException("无法申请");
        }
    }

    @Override
    public void CancelApply(int userID, int orderID) {
        if (orderService.GetState(orderID) != 1)
            throw new NuaaAntException("无法取消申请");
        applyMapper.CancelApply(userID, orderID);
    }

    @Override
    public List<Map<String, Object>> GetAppliers(int orderID) {
        var res = applyMapper.GetAppliers(orderID);
        if (res == null)
            throw new NuaaAntException("找不到需求");
        return res;
    }

}
