package com.clubie.nuaaant.user;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

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

    @Select("SELECT AvatarUrl, NickName, Gender, Motto, CollegeIndex, " +
            "       GradeIndex, Dormitory, StudentID, RealName, Phone, Email " +
            "FROM Users " +
            "WHERE ID = #{userID}")
    User GetUserInfo(int userID);

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

    @Select("SELECT AvatarUrl, NickName " +
            "FROM Users WHERE ID = #{userID}")
    Map<String, Object> GetBasicInfo(int userID);

    @Select("SELECT ROUND(GiverScoreTotal / GiverCount, 1) AS GiverScore, " +
            "       ROUND(TakerScoreTotal / TakerCount, 1) AS TakerScore, " +
            "       GiverCount, TakerCount " +
            "FROM Users WHERE ID = #{userID}")
    Map<String, Object> GetUserScores(int userID);
}
