package com.clubie.nuaaant.order;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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
            "    #{userID}, 1, 1, #{publishTime}, #{order.TypeIndex}, #{order.Title}, " +
            "    #{order.Reward}, #{order.Deadline}, #{order.romAddr}, #{order.ToAddr}, " +
            "    #{order.IsSelf}, #{order.SizeIndex}, #{order.WeightIndex}, #{order.ExpressCode}, " +
            "    #{order.QuestionTypeIndex}, #{order.Duration}, #{order.UnitIndex}, " +
            "    #{order.ReturnTime}, #{order.SimpleDesc}, #{order.DetailedDesc}" +
            ")")
    void AddOrder(int userID, Order order, Date publishTime);

    @Select("SELECT GiverID FROM Orders WHERE ID = #{orderID}")
    Integer GetGiver(int orderID);

    // TODO
    @Select("SELECT o.ID, TypeIndex, Deadline, Title, Reward, u.AvatarUrl," +
            "       u.NickName AS GiverName, 4.5 AS GiverScore, 5 AS TakerCount, " +
            "       5 AS LikeCount, 5 AS RemarkCount " +
            "FROM Orders o " +
            "INNER JOIN Users u ON o.GiverID = u.ID " +
            "WHERE NOT IsTemplate")
    List<Map<String, Object>> GetOrderList();

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
}
