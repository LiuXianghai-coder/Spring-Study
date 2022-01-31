package org.xhliu.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.xhliu.demo.entity.UserInfo;

import java.util.Optional;

/**
 * @author xhliu
 * @time 2022-01-28-下午9:30
 */
@Repository
public interface UserInfoRepo extends JpaRepository<UserInfo, Long> {
    Optional<UserInfo> findByUserName(String userName);
}
