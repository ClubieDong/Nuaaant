package com.clubie.nuaaant.apply;

import java.util.List;
import java.util.Map;

public interface ApplyService {
    void Apply(int userID, int orderID);

    void CancelApply(int userID, int orderID);

    List<Map<String, Object>> GetAppliers(int orderID);
}
