package org.xhliu.aop.service;

import org.xhliu.aop.entity.User;

public interface UserService {
    User createUser(String firstName, String lastName);

    User queryUser();
}
