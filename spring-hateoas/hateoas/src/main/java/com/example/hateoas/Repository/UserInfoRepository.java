package com.example.hateoas.Repository;

import com.example.hateoas.Entity.UserInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 定义的用户数据库访问接口， Spring 的 JPA 可以帮助我们完成基本的数据库操作。
 * 通过继承 CrudRepository<T, ID> 接口， Spring将会自动生成数据库访问对象，
 * 而我们只需要按照它的词法结构定义接口函数便可完成对数据库的操作
 */
@Repository
public interface UserInfoRepository extends CrudRepository<UserInfo, Long> {
    /*
        通过 Pageable 对象访问数据库， 从而实现分页的效果
     */
    List<UserInfo> findAll(@Param("page") Pageable page);

    /*
        通过 userId 属性来查找对应 UserInfo 对象
     */
    UserInfo findUserInfoByUserId(@Param("userId") Long userId);
}
