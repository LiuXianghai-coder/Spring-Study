package com.example.demo.app;

import com.example.demo.entity.Elvis;
import com.example.demo.entity.Example;

/**
 * @author xhliu2
 * @create 2021-09-06 16:19
 **/
public class Application {
    public static void main(String[] args) {
        System.out.println(new Example());
        System.out.println(Example.getSimpleInstance().toString());
        System.out.println(Example.getInstance().toString());
    }
}
