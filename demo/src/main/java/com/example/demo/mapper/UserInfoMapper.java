package com.example.demo.mapper;

import com.example.demo.entity.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lxh
 */
public interface UserInfoMapper {
    UserInfo getUserById(@Param("id") long id);

    List<UserInfo> selectByName(@Param("name") String name);

    List<UserInfo> selectByAge(@Param("age") int age);

    int insert(UserInfo userInfo);
}
