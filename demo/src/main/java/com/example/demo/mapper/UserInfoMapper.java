package com.example.demo.mapper;

import com.example.demo.entity.TaskInfo;
import com.example.demo.entity.UserInfo;
import com.example.demo.entity.UserInfoView;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lxh
 */
public interface UserInfoMapper {
    UserInfo getUserById(@Param("id") long id);

    List<UserInfo> selectByName(@Param("name") String name);

    List<UserInfo> selectByAge(@Param("age") int age);

    List<UserInfo> selectByType(@Param("param") TaskInfo param);

    List<UserInfoView> selectViewsById(@Param("id") long id);

    int insert(UserInfo userInfo);

    List<UserInfo> selectByParam(@Param("param") UserInfo param);
}
