package com.clubie.nuaaant.user;

import java.util.Map;

public interface UserService {
    Map<String, Object> Login(String code);

    User GetUserInfo(int userID);

    void SetUserInfo(String sessionID, User user);

    int Session2ID(String sessionID);

    Map<String, Object> GetBasicInfo(int userID);

    Map<String, Object> GetUserScores(int userID);
}
