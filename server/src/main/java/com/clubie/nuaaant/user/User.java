package com.clubie.nuaaant.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    private int ID;
    private String Name;
    @JsonProperty("openid")
    private String OpenID;
    @JsonProperty("session_key")
    private String SessionKey;
    private String SessionID;
}
