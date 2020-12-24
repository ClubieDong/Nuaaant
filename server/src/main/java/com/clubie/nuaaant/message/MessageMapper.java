package com.clubie.nuaaant.message;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface MessageMapper {
    @Insert("INSERT INTO Messages(SenderID, ReceiverID, Time, Text) " +
            "VALUES (#{senderID}, #{receiverID}, #{time}, #{text})")
    void AddMessage(int senderID, int receiverID, Date time, String text);

    @Select("SELECT ID, SenderID, Time, Text FROM Messages " +
            "WHERE (SenderID = #{senderID} AND ReceiverID = #{receiverID}) " +
            "   OR (SenderID = #{receiverID} AND ReceiverID = #{senderID})")
    List<Map<String, Object>> GetMessage(int senderID, int receiverID);

    @Update("UPDATE Messages SET HasRead = 1 " +
            "WHERE SenderID = #{senderID} AND ReceiverID = #{receiverID}")
    void ReadAll(int senderID, int receiverID);

    @Select("SELECT SenderID + ReceiverID - #{userID} AS UserID, " +
            "       (SELECT NickName FROM Users WHERE ID = UserID) As NickName, " +
            "       (SELECT AvatarUrl FROM Users WHERE ID = UserID) AS AvatarUrl, " +
            "       (SELECT Max(Time) FROM Messages WHERE SenderID = UserID OR ReceiverID = UserID) AS Time, " +
            "       (SELECT Text FROM Messages WHERE SenderID = UserID OR ReceiverID = UserID " +
            "        ORDER BY Time DESC LIMIT 1) AS Text, " +
            "       (SELECT Count(*) FROM Messages WHERE SenderID = UserID AND NOT HasRead) As NotRead " +
            "FROM Messages " +
            "WHERE SenderID = #{userID} OR ReceiverID = #{userID} " +
            "GROUP BY UserID " +
            "Order By Time DESC")
    List<Map<String, Object>> GetMessageList(int userID);
}
