package com.clubie.nuaaant.template;

import com.clubie.nuaaant.order.OrderService;
import com.clubie.nuaaant.utils.NuaaAntException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TemplateServiceImpl implements TemplateService {
    @Autowired
    private TemplateMapper templateMapper;

    @Autowired
    private OrderService orderService;

    @Override
    public List<Map<String, Object>> GetTemplateList(int userID) {
        var res = templateMapper.GetTemplateList(userID);
        if (res == null)
            throw new NuaaAntException("无法获取模板列表");
        return res;
    }

    @Override
    public void AddTemplate(int userID, Template template) {
        templateMapper.AddTemplate(userID, template);
    }

    @Override
    public Template GetTemplate(int templateID) {
        var res = templateMapper.GetTemplate(templateID);
        if (res == null)
            throw new NuaaAntException("找不到模板");
        return res;
    }

    @Override
    public void DeleteTemplate(int userID, int templateID) {
        var giverID = orderService.GetGiverID(templateID);
        if (userID != giverID)
            throw new NuaaAntException("无权限");
        templateMapper.DeleteTemplate(templateID);
    }
}
