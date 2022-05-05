package com.example.demo.entity;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;

/**
 * @author xhliu
 * @create 2022-04-06-11:10
 **/

public enum Type {
    YEAR("year"),
    MONTH("month");

    final String des;

    Type(String des) {
        this.des = des;
    }

    public static void main(String[] args) {
    }
}
