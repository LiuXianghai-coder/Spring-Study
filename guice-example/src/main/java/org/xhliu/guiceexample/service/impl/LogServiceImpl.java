package org.xhliu.guiceexample.service.impl;

import org.xhliu.guiceexample.service.LogService;

/**
 * @author xhliu
 * @create 2022-02-17-15:00
 **/
public class LogServiceImpl implements LogService {
    @Override
    public void log(CharSequence msg) {
        System.out.println("--------log: " + msg);
    }
}
