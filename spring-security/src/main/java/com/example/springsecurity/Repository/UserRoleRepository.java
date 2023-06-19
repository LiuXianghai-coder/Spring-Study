package com.example.springsecurity.Repository;

import com.example.springsecurity.Entity.UserRole;
import org.springframework.data.repository.CrudRepository;

public interface UserRoleRepository extends CrudRepository<UserRole, Long> {
    /**
     * 通过用户名来查找对应的用户对象
     * @param userName ： 查找的用户名
     * @return ： 查找到的用户角色对象
     */
    UserRole findUserRoleByUsername(String userName);
}
