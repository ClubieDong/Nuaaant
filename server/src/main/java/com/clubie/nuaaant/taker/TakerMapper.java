package com.clubie.nuaaant.taker;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.Date;

@Mapper
public interface TakerMapper {
    @Update("UPDATE Orders SET" +
            "    TakerID = NULL, " +
            "    State = 1, " +
            "    AcceptTime = NULL " +
            "WHERE ID = #{orderID}")
    void GiveUp(int orderID);

    @Update("UPDATE Orders SET" +
            "    State = 3, " +
            "    SubmitTime = #{submitTime} " +
            "WHERE ID = #{orderID}")
    void Submit(int orderID, Date submitTime);

    @Update("UPDATE Orders SET" +
            "    State = 2, " +
            "    SubmitTime = NULL " +
            "WHERE ID = #{orderID}")
    void CancelSubmit(int orderID);
}
