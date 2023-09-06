package com.example.demo.mapper;

import com.example.demo.entity.TaskInfo;
import com.example.demo.entity.UserInfo;
import com.example.demo.entity.UserInfoView;
import com.example.demo.plugin.ConfigMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.session.RowBounds;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author lxh
 */
public interface UserInfoMapper extends Mapper<UserInfo> {
    UserInfo getUserById(@Param("id") long id);

    List<UserInfo> selectByName(@Param("name") String name);

    List<UserInfo> selectByAge(@Param("age") int age);

    List<UserInfo> selectByType(@Param("param") TaskInfo param);

    List<UserInfoView> selectViewsById(@Param("id") long id);

    List<UserInfo> selectByRowBound(RowBounds rowBounds);

    int insert(UserInfo userInfo);

    @SelectProvider(value = ConfigMapper.class, method = "selectByUserId")
    UserInfo selectByUserId(@Param("userId") String userId);

    List<UserInfo> selectByParam(@Param("param") UserInfo param);

    int insertAll(@Param("param") List<UserInfo> data);
}
