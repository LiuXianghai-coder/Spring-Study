package com.example.demo.service;

import com.example.demo.proxy.UserInfoServiceProxy;
import com.example.demo.proxy.UserServiceProxy;
import net.sf.cglib.proxy.Enhancer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author lxh
 */
@Service
@Transactional
public class UserInfoService implements UserService {

    @Resource
    private UserInfoMapper userInfoMapper;

    public int save(UserInfo userInfo) {
        return userInfoMapper.insert(userInfo);
    }

    public UserInfo query(long id) {
        UserInfo info = new UserInfo();
        info.setUserId(1L);
        info.setUserGender("Male");
        info.setUserName("xhliu");
        return info;
    }

    protected int update(UserInfo info) {
        return 0;
    }

    public static void main(String[] args) {
        UserServiceProxy proxy = new UserServiceProxy();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        UserService obj = (UserService) Proxy.newProxyInstance(loader, new Class[]{UserService.class}, proxy);
        UserInfo info = obj.query(1L);
        System.out.println(info);

        Method[] methods = obj.getClass().getDeclaredMethods();
        for (Method method : methods) {
            System.out.println(method.getReturnType() + "\t" + method.getName());
        }

    }
}
