package com.clubie.nuaaant.user;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {

    @Select("SELECT COUNT(*) FROM Users WHERE OpenID = #{openID} LIMIT 1")
    int IsOpenIDExist(String openID);

    @Insert("INSERT INTO Users(OpenID, SessionKey, SessionID) " +
            "Values(#{openID}, #{sessionKey}, #{sessionID})")
    void CreateUser(String openID, String sessionKey, String sessionID);

    @Update("UPDATE Users SET " +
            "    SessionKey = #{sessionKey}, " +
            "    SessionID = #{sessionID} " +
            "WHERE OpenID = #{openID}")
    void UpdateSession(String openID, String sessionKey, String sessionID);

    @Select("SELECT ID, AvatarUrl, NickName, Gender, Motto, CollegeIndex, " +
            "       GradeIndex, Dormitory, StudentID, RealName, Phone, Email " +
            "FROM Users " +
            "WHERE SessionID = #{sessionID}")
    User GetUserBySessionID(String sessionID);

    @Update("UPDATE Users SET " +
            "    AvatarUrl = #{user.AvatarUrl}, " +
            "    NickName = #{user.NickName}, " +
            "    Gender = #{user.Gender}, " +
            "    Motto = #{user.Motto}, " +
            "    CollegeIndex = #{user.CollegeIndex}, " +
            "    GradeIndex = #{user.GradeIndex}, " +
            "    Dormitory = #{user.Dormitory}, " +
            "    StudentID = #{user.StudentID}, " +
            "    RealName = #{user.RealName}, " +
            "    Phone = #{user.Phone}, " +
            "    Email = #{user.Email} " +
            "WHERE SessionID = #{sessionID}")
    void UpdateUserBySessionID(String sessionID, User user);

    @Select("SELECT ID FROM Users WHERE SessionID = #{sessionID}")
    Integer GetUserIDBySessionID(String sessionID);
}
