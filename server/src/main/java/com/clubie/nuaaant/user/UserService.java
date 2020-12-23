package com.clubie.nuaaant.user;

import java.util.Map;

public interface UserService {
    Map<String, Object> Login(String code);

    User GetUserInfo(String sessionID);

    void SetUserInfo(String sessionID, User user);

    int GetUserIDBySessionID(String sessionID);

    Map<String, Object> GetBasicInfoByID(int id);
}
