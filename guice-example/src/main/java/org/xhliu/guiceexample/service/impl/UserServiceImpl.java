package org.xhliu.guiceexample.service.impl;

import org.xhliu.guiceexample.service.UserService;

/**
 * @author xhliu
 * @create 2022-02-17-14:59
 **/
public class UserServiceImpl implements UserService {
    @Override
    public void process() {
        System.out.println("正在处理用户相关的服务。。。");
    }
}
