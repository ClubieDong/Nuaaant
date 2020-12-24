package com.clubie.nuaaant.remark;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface RemarkMapper {
    @Insert("INSERT INTO Remarks(UserID, OrderID, Time, Text) " +
            "VALUES (#{userID}, #{orderID}, #{time}, #{text})")
    void AddRemark(int userID, int orderID, Date time, String text);

    @Select("SELECT r.ID, u.AvatarUrl, u.NickName, r.Time, r.Text, " +
            "       (SELECT COUNT(*) FROM Agrees WHERE RemarkID = r.ID) AS AgreeCount, " +
            "       (SELECT COUNT(*) FROM Agrees WHERE RemarkID = r.ID AND UserID = #{userID} LIMIT 1) AS Agreed " +
            "FROM Remarks r " +
            "INNER JOIN Users u ON r.UserID = u.ID " +
            "WHERE r.OrderID = #{orderID}")
    List<Map<String, Object>> GetRemarks(int userID, int orderID);
}
