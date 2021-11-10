package org.xhliu.aop.service.impl;

import org.xhliu.aop.entity.User;
import org.xhliu.aop.service.UserService;

/**
 * @author xhliu2
 * @create 2021-11-10 16:36
 **/
public class UserServiceImpl implements UserService {
    private static User user = null;

    public User createUser(String firstName, String lastName) {
        user = new User(firstName, lastName);
        return user;
    }

    public User queryUser() {
        return user;
    }
}
