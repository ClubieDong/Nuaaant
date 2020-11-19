package com.clubie.nuaaant.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class ReturnWrapper {

    public static Map<String, Object> Success(Object obj) {
        var res = new LinkedHashMap<String, Object>();
        res.put("success", true);
        res.put("data", obj);
        return res;
    }

    public static Map<String, Object> Fail(Exception e) {
        var res = new LinkedHashMap<String, Object>();
        res.put("success", false);
        res.put("errMsg", e.getMessage());
        return res;
    }

}
