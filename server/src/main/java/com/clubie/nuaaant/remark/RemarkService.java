package com.clubie.nuaaant.remark;

import java.util.List;
import java.util.Map;

public interface RemarkService {
    void AddRemark(int userID, int orderID, String text);

    List<Map<String, Object>> GetRemarks(int userID, int orderID);
}
