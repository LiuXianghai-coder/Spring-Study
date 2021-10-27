package com.example.demo.entity;

/**
 * @author xhliu2
 * @create 2021-09-06 16:15
 **/
public class Elvis {
    private static final Elvis INSTANCE = new Elvis();
    private Elvis(){}

    public static Elvis getInstance() {return INSTANCE;}
}
