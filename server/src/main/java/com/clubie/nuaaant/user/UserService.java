package com.clubie.nuaaant.user;

public interface UserService {
    String Login(String code);

    User GetUserInfo(String sessionID);

    void SetUserInfo(String sessionID, User user);

    int GetUserIDBySessionID(String sessionID);
}
