package com.example.demo.entity;

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
        int result = (int) (Math.log(Long.MAX_VALUE) / Math.log(17));
        System.out.println(result);
    }
}
