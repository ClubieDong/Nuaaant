package com.clubie.nuaaant.user;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface UserService {
    String Login(String code);
    User GetUserInfo(String sessionID);
    void SetUserInfo(String sessionID, User user);
}
