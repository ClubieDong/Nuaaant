package com.clubie.nuaaant.order;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {
    @Insert("INSERT INTO Orders(" +
            "    GiverID, IsTemplate, State, PublishTime, TypeIndex, Title, Reward, " +
            "    Deadline, FromAddr, ToAddr, IsSelf, SizeIndex, WeightIndex, " +
            "    ExpressCode, QuestionTypeIndex, Duration, UnitIndex, ReturnTime, " +
            "    SimpleDesc, DetailedDesc " +
            ") VALUES(" +
            "    #{userID}, 0, 1, #{publishTime}, #{order.TypeIndex}, #{order.Title}, " +
            "    #{order.Reward}, #{order.Deadline}, #{order.FromAddr}, #{order.ToAddr}, " +
            "    #{order.IsSelf}, #{order.SizeIndex}, #{order.WeightIndex}, #{order.ExpressCode}, " +
            "    #{order.QuestionTypeIndex}, #{order.Duration}, #{order.UnitIndex}, " +
            "    #{order.ReturnTime}, #{order.SimpleDesc}, #{order.DetailedDesc}" +
            ")")
    void AddOrder(int userID, Order order, Date publishTime);

    @Select("SELECT GiverID FROM Orders WHERE ID = #{orderID}")
    Integer GetGiver(int orderID);

    @Select("SELECT TakerID FROM Orders WHERE ID = #{orderID}")
    Integer GetTaker(int orderID);

    @Select("SELECT o.ID, o.TypeIndex, o.Deadline, o.Title, o.Reward, " +
            "       u.AvatarUrl AS GiverAvatarUrl, u.NickName AS GiverName, " +
            "       ROUND(u.GiverScoreTotal / u.GiverCount, 1) AS GiverScore, " +
            "       (SELECT COUNT(*) FROM Appliers WHERE OrderID = o.ID) AS TakerCount, " +
            "       (SELECT COUNT(*) FROM Likes WHERE OrderID = o.ID) AS LikeCount, " +
            "       0 AS RemarkCount, o.PublishTime " +
            "FROM Orders o " +
            "LEFT JOIN Users u ON o.GiverID = u.ID " +
            "WHERE NOT o.IsTemplate " +
            "      AND (#{searchText} = '%%' OR o.Title LIKE #{searchText}) " +
            "      AND (#{typeIndex} = 0 OR o.TypeIndex = #{typeIndex})")
    List<Map<String, Object>> GetOrderList(int userID, String searchText, int typeIndex);

    @Select("SELECT TypeIndex, Title, Reward, Deadline, FromAddr, ToAddr, IsSelf, SizeIndex, " +
            "       WeightIndex, ExpressCode, QuestionTypeIndex, Duration, UnitIndex, " +
            "       ReturnTime, SimpleDesc, DetailedDesc, State, PublishTime, AcceptTime, " +
            "       SubmitTime, CompleteTime, GiverID, TakerID " +
            "FROM Orders " +
            "WHERE ID = #{orderID}")
    Map<String, Object> GetOrder(int orderID);

    @Select("SELECT State FROM Orders WHERE ID = #{orderID}")
    Integer GetState(int orderID);

    @Select("SELECT Count(*) FROM Orders WHERE ID = #{orderID} LIMIT 1")
    int CheckExist(int orderID);

    @Update("UPDATE Orders SET" +
            "    TypeIndex = #{order.TypeIndex}, " +
            "    Title = #{order.Title}, " + 
            "    Reward = #{order.Reward}, " + 
            "    Deadline = #{order.Deadline}, " + 
            "    FromAddr = #{order.FromAddr}, " + 
            "    ToAddr = #{order.ToAddr}, " + 
            "    IsSelf = #{order.IsSelf}, " + 
            "    SizeIndex = #{order.SizeIndex}, " + 
            "    WeightIndex = #{order.WeightIndex}, " + 
            "    ExpressCode = #{order.ExpressCode}, " + 
            "    QuestionTypeIndex = #{order.QuestionTypeIndex}, " + 
            "    Duration = #{order.Duration}, " + 
            "    UnitIndex = #{order.UnitIndex}, " + 
            "    ReturnTime = #{order.ReturnTime}, " + 
            "    SimpleDesc = #{order.SimpleDesc}, " + 
            "    DetailedDesc = #{order.DetailedDesc} " +
            "WHERE ID = #{orderID}")
    void EditOrder(int orderID, Order order);

    @Select("SELECT o.ID, o.State, o.TypeIndex, o.Deadline, o.Title, o.Reward, " +
            "       u.AvatarUrl AS TakerAvatarUrl, u.NickName AS TakerName, " +
            "       ROUND(u.TakerScoreTotal / u.TakerCount, 1) AS TakerScore, " +
            "       (SELECT COUNT(*) FROM Appliers WHERE OrderID = o.ID) AS TakerCount, " +
            "       (SELECT COUNT(*) FROM Likes WHERE OrderID = o.ID) AS LikeCount, " +
            "       0 AS RemarkCount, " +
            "       o.PublishTime, o.AcceptTime, o.SubmitTime, o.CompleteTime " +
            "FROM Orders o " +
            "LEFT JOIN Users u ON o.TakerID = u.ID " +
            "WHERE NOT o.IsTemplate AND o.GiverID = #{userID} " +
            "      AND (#{searchText} = '%%' OR o.Title LIKE #{searchText}) " +
            "      AND (#{typeIndex} = 0 OR o.TypeIndex = #{typeIndex})")
    List<Map<String, Object>> GetUserOrderList(int userID, String searchText, int typeIndex);
}