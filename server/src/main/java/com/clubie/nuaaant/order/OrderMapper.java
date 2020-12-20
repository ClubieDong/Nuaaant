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
            "    DetailedDesc" +
            ") VALUES(" +
            "    #{TypeIndex}, #{Title}, #{Reward}, #{Deadline}, #{GiverID}, " +
            "    #{TakerID}, #{IsTemplate}, #{FromAddr}, #{ToAddr}, #{IsSelf}, " +
            "    #{SizeIndex}, #{WeightIndex}, #{ExpressCode}, #{QuestionTypeIndex}, " +
            "    #{Duration}, #{UnitIndex}, #{ReturnTime}, #{SimpleDesc}, " +
            "    #{DetailedDesc}" +
            ")")
    void AddOrder(Order order);

    @Select("SELECT GiverID FROM Orders WHERE ID = #{id}")
    Integer GetGiverByID(int id);

    @Select("SELECT ID, TypeIndex, Title, Reward, Deadline, GiverID, TakerID, " +
            "       IsTemplate, FromAddr, ToAddr, IsSelf, SizeIndex, WeightIndex, " +
            "       ExpressCode, QuestionTypeIndex, Duration, UnitIndex, ReturnTime, " +
            "       SimpleDesc, DetailedDesc " +
            "FROM Orders " +
            "WHERE ID = #{id}")
    Order GetOrderByID(int id);

    @Delete("DELETE FROM Orders WHERE ID = #{id}")
    void DeleteOrderByID(int id);
}
