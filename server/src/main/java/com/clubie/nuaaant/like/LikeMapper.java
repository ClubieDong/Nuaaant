package com.clubie.nuaaant.like;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LikeMapper {
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
}
