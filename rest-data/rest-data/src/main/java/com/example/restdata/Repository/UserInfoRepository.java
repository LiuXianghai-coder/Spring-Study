package com.example.restdata.Repository;

import com.example.restdata.Entity.UserInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: liuxianghai
 * Date: 2021/1/6
 * Time: 下午4:43
 */
@Repository
public interface UserInfoRepository extends CrudRepository<UserInfo, Long> {
    List<UserInfo> findAll(@Param("page") Pageable page);

    UserInfo findUserInfoByUserId(@Param("userId") Long userId);
}

