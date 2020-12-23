package com.clubie.nuaaant.giver;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.Date;

@Mapper
public interface GiverMapper {
    @Update("UPDATE Orders SET" +
            "    State = 1, " +
            "    PublishTime = #{publishTime} " +
            "WHERE ID = #{orderID}")
    void Republish(int orderID, Date publishTime);

    @Update("UPDATE Orders SET" +
            "    State = 0, " +
            "    PublishTime = NULL " +
            "WHERE ID = #{orderID}")
    void Withdraw(int orderID);

    @Update("UPDATE Orders SET" +
            "    TakerID = #{takerID}, " +
            "    State = 2, " +
            "    AcceptTime = #{acceptTime} " +
            "WHERE ID = #{orderID}")
    void ChooseTaker(int orderID, int takerID, Date acceptTime);

    @Update("UPDATE Orders SET" +
            "    TakerID = NULL, " +
            "    State = 1, " +
            "    AcceptTime = NULL " +
            "WHERE ID = #{orderID}")
    void CancelChooseTaker(int orderID);

    @Update("UPDATE Orders SET" +
            "    State = 4, " +
            "    CompleteTime = #{completeTime} " +
            "WHERE ID = #{orderID}")
    void Accept(int orderID, Date completeTime);

    @Update("UPDATE Orders SET" +
            "    State = 2, " +
            "    CompleteTime = NULL " +
            "WHERE ID = #{orderID}")
    void Reject(int orderID);
}
