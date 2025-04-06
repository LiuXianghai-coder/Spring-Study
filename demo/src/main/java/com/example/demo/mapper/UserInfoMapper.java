package com.example.demo.mapper;

import com.example.demo.entity.TaskInfo;
import com.example.demo.entity.UserInfo;
import com.example.demo.entity.UserInfoView;
import com.example.demo.plugin.ConfigMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * @author lxh
 */
@Mapper
public interface UserInfoMapper extends ExtendsMapper<UserInfo> {
    UserInfo getUserById(@Param("id") long id);

    List<UserInfo> selectByName(@Param("name") String name);

    List<UserInfo> selectByAge(@Param("age") int age);

    List<UserInfo> selectByType(@Param("param") TaskInfo param);

    List<UserInfoView> selectViewsById(@Param("id") long id);

    List<UserInfo> selectByRowBound(RowBounds rowBounds);

    int insert(UserInfo userInfo);

    @Update(value = {"UPDATE tb_user SET age=#{age} WHERE id=#{id}"})
    int updateUserAge(@Param("age") Integer age, @Param("id") Long id);

    @SelectProvider(value = ConfigMapper.class, method = "selectByUserId")
    UserInfo selectByUserId(@Param("userId") String userId);

    List<UserInfo> selectByParam(@Param("param") UserInfo param);

    int insertAll(@Param("param") List<UserInfo> data);

    @Update(value = {
            "<script>" +
                    "<foreach collection=\"param\" item=\"item\" separator=\";\">" +
                    "UPDATE user_info ui SET simple_id='0x3f3f3f3f' WHERE ui.user_id=#{item.id}" +
                    "</foreach>" +
                    "</script>"
    })
    int updateAll(@Param("param") List<? extends UserInfo> userInfos);
}
