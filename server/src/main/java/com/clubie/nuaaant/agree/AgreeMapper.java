package com.clubie.nuaaant.agree;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AgreeMapper {
    @Insert("INSERT INTO Agrees (UserID, RemarkID) VALUES (#{userID}, #{remarkID})")
    void Agree(int userID, int remarkID);

    @Delete("DELETE FROM Agrees WHERE UserID = #{userID} AND RemarkID = #{remarkID}")
    void CancelAgree(int userID, int remarkID);
}
