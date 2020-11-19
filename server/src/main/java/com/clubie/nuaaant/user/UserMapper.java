package com.clubie.nuaaant.user;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {

    @Select("SELECT COUNT(*) FROM Users WHERE OpenID = #{openID} LIMIT 1")
    int IsOpenIDExist(String openID);

    @Insert("INSERT INTO Users(OpenID, SessionKey, SessionID) Values(#{openID}, #{sessionKey}, #{sessionID})")
    void CreateUser(String openID, String sessionKey, String sessionID);

    @Update("UPDATE Users SET SessionKey = #{sessionKey}, SessionID = #{sessionID} WHERE OpenID = #{openID}")
    void UpdateSession(String openID, String sessionKey, String sessionID);

}
