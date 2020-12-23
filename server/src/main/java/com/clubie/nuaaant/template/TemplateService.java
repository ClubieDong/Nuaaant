package com.clubie.nuaaant.template;

import java.util.List;
import java.util.Map;

public interface TemplateService {
    List<Map<String, Object>> GetTemplateList(int userID);

    void AddTemplate(int userID, Template template);

    Template GetTemplate(int templateID);

    void DeleteTemplate(int userID, int templateID);
}
