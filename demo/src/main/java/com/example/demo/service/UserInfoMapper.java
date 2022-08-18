package com.example.demo.service;

import org.apache.ibatis.annotations.*;

/**
 * @author lxh
 */
@Mapper
public interface UserInfoMapper {
    @Select(value = "SELECT * FROM user_info WHERE user_id=#{userId}")
    @Results(value = {
            @Result(property = "userId", column = "user_id"),
            @Result(property = "userName", column = "user_name"),
            @Result(property = "userGender", column = "user_gender")
    })
    UserInfo selectById(@Param("userId") long userId);

    @Insert(value = "INSERT INTO user_info(user_id, user_name, user_gender) VALUES(#{userId}, #{userName}, #{userGender})")
    int insert(UserInfo userInfo);
}
