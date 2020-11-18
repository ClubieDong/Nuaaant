package com.clubie.nuaaant.mappers;

import com.clubie.nuaaant.entities.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM User WHERE NAME = #{name}")
    User findByName(@Param("name") String name);

}
