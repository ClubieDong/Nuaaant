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
        String sessionID = UUID.randomUUID().toString();
        if (userMapper.IsOpenIDExist(openID) == 0)
            userMapper.CreateUser(openID, sessionKey, sessionID);
        else
            userMapper.UpdateSession(openID, sessionKey, sessionID);
        return sessionID;
    }

}
