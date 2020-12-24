package com.clubie.nuaaant.remark;

import com.clubie.nuaaant.order.OrderMapper;
import com.clubie.nuaaant.order.OrderService;
import com.clubie.nuaaant.utils.NuaaAntException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class RemarkServiceImpl implements RemarkService {

    @Autowired
    private OrderService orderService;
    @Autowired
    private RemarkMapper remarkMapper;

    @Override
    public void AddRemark(int userID, int orderID, String text) {
        if (!orderService.CheckExist(orderID))
            throw new NuaaAntException("找不到需求");
        if (text == null || text.isBlank())
            throw new NuaaAntException("评论内容不能为空");
        remarkMapper.AddRemark(userID, orderID, new Date(), text);
    }

    @Override
    public List<Map<String, Object>> GetRemarks(int userID, int orderID) {
        var res = remarkMapper.GetRemarks(userID, orderID);
        if (res == null)
            throw new NuaaAntException("无法获取评论");
        return res;
    }
}
