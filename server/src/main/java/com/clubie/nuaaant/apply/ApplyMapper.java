package com.clubie.nuaaant.apply;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface ApplyMapper {
    @Insert("INSERT INTO Appliers (UserID, OrderID) VALUES (#{userID}, #{orderID})")
    void Apply(int userID, int orderID);

    @Delete("DELETE FROM Appliers WHERE UserID = #{userID} AND OrderID = #{orderID}")
    void CancelApply(int userID, int orderID);

    @Select("SELECT u.ID, u.AvatarUrl, u.NickName, 4.5 AS Score " +
            "FROM Users u " +
            "INNER JOIN Appliers a ON a.UserID = u.ID " +
            "WHERE a.OrderID = #{orderID}")
    List<Map<String, Object>> GetAppliers(int orderID);

    @Select("SELECT Count(*) FROM Appliers " +
            "WHERE OrderID = #{orderID} AND UserID = #{userID} " +
            "LIMIT 1")
    int IsApplier(int orderID, int userID);

    @Delete("DELETE FROM Appliers WHERE OrderID = #{orderID}")
    void DeleteAllAppliers(int orderID);
}
