package com.clubie.nuaaant.template;

import com.clubie.nuaaant.order.Order;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface TemplateMapper {
    @Select("SELECT ID, TypeIndex, Title, GiverID IS NOT NULL As UserDefine " +
            "FROM Orders " +
            "WHERE (GiverID = #{userID} AND IsTemplate = TRUE) OR GiverID IS NULL " +
            "ORDER BY UserDefine DESC, ID DESC")
    List<Map<String, Object>> GetTemplateList(int userID);

    @Insert("INSERT INTO Orders(" +
            "    GiverID, IsTemplate, TypeIndex, Title, Reward, Deadline, " +
            "    FromAddr, ToAddr, IsSelf, SizeIndex, WeightIndex, ExpressCode, " +
            "    QuestionTypeIndex, Duration, UnitIndex, ReturnTime, SimpleDesc, " +
            "    DetailedDesc " +
            ") VALUES(" +
            "    #{userID}, 1, #{template.TypeIndex}, #{template.Title}, #{template.Reward}, " +
            "    #{template.Deadline}, #{template.FromAddr}, #{template.ToAddr}, " +
            "    #{template.IsSelf}, #{template.SizeIndex}, #{template.WeightIndex}, " +
            "    #{template.ExpressCode}, #{template.QuestionTypeIndex}, #{template.Duration}, " +
            "    #{template.UnitIndex}, #{template.ReturnTime}, #{template.SimpleDesc}, " +
            "    #{template.DetailedDesc}" +
            ")")
    void AddTemplate(int userID, Template template);

    @Select("SELECT ID, TypeIndex, Title, Reward, Deadline, FromAddr, ToAddr, IsSelf, " +
            "       SizeIndex, WeightIndex, ExpressCode, QuestionTypeIndex, Duration, " +
            "       UnitIndex, ReturnTime, SimpleDesc, DetailedDesc " +
            "FROM Orders " +
            "WHERE ID = #{templateID} AND IsTemplate")
    Template GetTemplate(int templateID);

    @Delete("DELETE FROM Orders WHERE ID = #{templateID} AND IsTemplate")
    void DeleteTemplate(int templateID);
}
