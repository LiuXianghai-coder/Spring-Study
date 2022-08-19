package com.example.demo.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author lxh
 */
public class UserServiceProxy implements InvocationHandler {

    private final static Logger log = LoggerFactory.getLogger(UserServiceProxy.class);

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("Method Name {} ", method.getName());
        return method.invoke(proxy, args);
    }
}
