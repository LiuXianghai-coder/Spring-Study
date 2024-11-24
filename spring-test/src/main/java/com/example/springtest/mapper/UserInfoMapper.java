package com.example.springtest.mapper;

import com.example.springtest.entity.UserInfo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

/**
 *@author lxh
 */
@Mapper
public interface UserInfoMapper {

    @Results(value = {
            @Result(column = "user_id", property = "userId", jdbcType = JdbcType.INTEGER),
            @Result(column = "user_name", property = "userName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "user_gender", property = "userGender", jdbcType = JdbcType.VARCHAR),
            @Result(column = "simple_id", property = "simpleId", jdbcType = JdbcType.VARCHAR),
            @Result(column = "backup_id", property = "backupId", jdbcType = JdbcType.VARCHAR)
    })
    @Select("SELECT user_id, user_name, user_gender, simple_id, backup_id" +
            " FROM user_info WHERE user_id=#{userId}")
    UserInfo findById(@Param("userId") Integer userId);
}
