package com.clubie.nuaaant.user;

import com.clubie.nuaaant.utils.NuaaAntException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private static final ObjectMapper jackson = new ObjectMapper();
    private static final RestTemplate restTemplate = new RestTemplate();
    private static final String url = "https://api.weixin.qq.com/sns/jscode2session?appid={APPID}&secret={SECRET}&js_code={CODE}&grant_type=authorization_code";
    private static final String appid = "wxbaac895ca0a7dae3";
    private static final String secret = "d667e8d29df9e929b30fcf6ffa3cf6d1";

    @Autowired
    private UserMapper userMapper;

    private void Nullify(User user) {
        if (user.getAvatarUrl().isEmpty())
            user.setAvatarUrl(null);
        if (user.getNickName().isEmpty())
            user.setNickName(null);
        if (user.getMotto().isEmpty())
            user.setMotto(null);
        if (user.getDormitory().isEmpty())
            user.setDormitory(null);
        if (user.getStudentID().isEmpty())
            user.setStudentID(null);
        if (user.getRealName().isEmpty())
            user.setRealName(null);
        if (user.getPhone().isEmpty())
            user.setPhone(null);
        if (user.getEmail().isEmpty())
            user.setEmail(null);
    }

    private void Emptify(User user) {
        if (user.getAvatarUrl() == null)
            user.setAvatarUrl("");
        if (user.getNickName() == null)
            user.setNickName("");
        if (user.getMotto() == null)
            user.setMotto("");
        if (user.getDormitory() == null)
            user.setDormitory("");
        if (user.getStudentID() == null)
            user.setStudentID("");
        if (user.getRealName() == null)
            user.setRealName("");
        if (user.getPhone() == null)
            user.setPhone("");
        if (user.getEmail() == null)
            user.setEmail("");
    }

    @Override
    public String Login(String code) {
        var response = restTemplate.getForObject(url, String.class, appid, secret, code);
        if (response == null)
            throw new NuaaAntException("微信服务器无响应");
        String sessionKey, openID;
        try {
            var map = jackson.readValue(response, Map.class);
            sessionKey = (String) map.get("session_key");
            openID = (String) map.get("openid");
        } catch (Exception e) {
            throw new NuaaAntException("JSON解析失败");
        }
        if (sessionKey == null || openID == null)
            throw new NuaaAntException("无效code");
        String sessionID = UUID.randomUUID().toString();
        if (userMapper.IsOpenIDExist(openID) == 0)
            userMapper.CreateUser(openID, sessionKey, sessionID);
        else
            userMapper.UpdateSession(openID, sessionKey, sessionID);
        return sessionID;
    }

    @Override
    public User GetUserInfo(String sessionID) {
        var res = userMapper.GetUserBySessionID(sessionID);
        if (res == null)
            throw new NuaaAntException("找不到用户！");
        Emptify(res);
        return res;
    }

    @Override
    public void SetUserInfo(String sessionID, User user) {
        Nullify(user);
        userMapper.UpdateUserBySessionID(sessionID, user);
    }

    @Override
    public int GetUserIDBySessionID(String sessionID) {
        var userID = userMapper.GetUserIDBySessionID(sessionID);
        if (userID == null)
            throw new NuaaAntException("找不到用户！");
        return userID;
    }

}
