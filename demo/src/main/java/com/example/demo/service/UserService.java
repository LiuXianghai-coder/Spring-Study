package com.example.demo.service;

/**
 * @author lxh
 */
public interface UserService {
    int save(UserInfo userInfo);

    UserInfo query(long id);
}
