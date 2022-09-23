package com.example.demo.mapper;

import com.example.demo.entity.UserInfo;
import org.apache.ibatis.annotations.Param;

/**
 * @author lxh
 */
public interface UserInfoMapper {
    UserInfo getUserById(@Param("id") long id);

    int insert(UserInfo userInfo);
}
