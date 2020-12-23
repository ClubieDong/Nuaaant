package com.clubie.nuaaant.order;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {
    @Select("SELECT ID, TypeIndex, Title, GiverID IS NOT NULL As UserDefine " +
            "FROM Orders " +
            "WHERE (GiverID = #{id} AND IsTemplate = TRUE) OR GiverID IS NULL " +
            "ORDER BY UserDefine DESC, ID DESC")
    List<Map<String, Object>> GetTemplateList(int id);

    @Insert("INSERT INTO Orders(" +
            "    TypeIndex, Title, Reward, Deadline, GiverID, TakerID, IsTemplate, " +
            "    FromAddr, ToAddr, IsSelf, SizeIndex, WeightIndex, ExpressCode, " +
            "    QuestionTypeIndex, Duration, UnitIndex, ReturnTime, SimpleDesc, " +
            "    DetailedDesc, State, PublishTime, AcceptTime, SubmitTime, " +
            "    CompleteTime " +
            ") VALUES(" +
            "    #{TypeIndex}, #{Title}, #{Reward}, #{Deadline}, #{GiverID}, " +
            "    #{TakerID}, #{IsTemplate}, #{FromAddr}, #{ToAddr}, #{IsSelf}, " +
            "    #{SizeIndex}, #{WeightIndex}, #{ExpressCode}, #{QuestionTypeIndex}, " +
            "    #{Duration}, #{UnitIndex}, #{ReturnTime}, #{SimpleDesc}, " +
            "    #{DetailedDesc}, #{State}, #{PublishTime}, #{AcceptTime}, " +
            "    #{SubmitTime}, #{CompleteTime}" +
            ")")
    void AddOrder(Order order);

    @Select("SELECT GiverID FROM Orders WHERE ID = #{id}")
    Integer GetGiverByID(int id);

    @Select("SELECT ID, TypeIndex, Title, Reward, Deadline, FromAddr, ToAddr, IsSelf, " +
            "       SizeIndex, WeightIndex, ExpressCode, QuestionTypeIndex, Duration, " +
            "       UnitIndex, ReturnTime, SimpleDesc, DetailedDesc " +
            "FROM Orders " +
            "WHERE ID = #{id} AND IsTemplate")
    Order GetTemplateByID(int id);

    @Delete("DELETE FROM Orders WHERE ID = #{id} AND IsTemplate")
    void DeleteTemplateByID(int id);

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
            "WHERE ID = #{id}")
    Map<String, Object> GetOrderByID(int id);

    @Select("SELECT State FROM Orders WHERE ID = #{orderID}")
    Integer GetOrderState(int orderID);

    @Insert("INSERT INTO Appliers (UserID, OrderID) VALUES (#{userID}, #{orderID})")
    void Apply(int userID, int orderID);

    @Delete("DELETE FROM Appliers WHERE UserID = #{userID} AND OrderID = #{orderID}")
    void CancelApply(int userID, int orderID);

    @Insert("INSERT INTO Likes (UserID, OrderID) VALUES (#{userID}, #{orderID})")
    void Like(int userID, int orderID);

    @Delete("DELETE FROM Likes WHERE UserID = #{userID} AND OrderID = #{orderID}")
    void CancelLike(int userID, int orderID);

    @Select("SELECT COUNT(*) FROM Likes " +
            "WHERE UserID = #{userID} AND OrderID = #{orderID} " +
            "LIMIT 1")
    int CheckLike(int userID, int orderID);

    @Select("SELECT COUNT(*) FROM Likes WHERE OrderID = #{orderID}")
    int GetLikeCount(int orderID);

    @Select("SELECT u.ID, u.AvatarUrl, u.NickName, 4.5 AS Score " +
            "FROM Users u " +
            "INNER JOIN Appliers a ON a.UserID = u.ID " +
            "WHERE a.OrderID = #{orderID}")
    List<Map<String, Object>> GetApplierInfo(int orderID);

    @Select("SELECT Count(*) FROM Orders WHERE ID = #{orderID} LIMIT 1")
    int CheckOrderExist(int orderID);
}
