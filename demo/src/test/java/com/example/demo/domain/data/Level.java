package com.example.demo.domain.data;

/**
 * @author xhliu
 * @create 2022-04-28-13:49
 **/
public enum Level {
    HIGH_SCHOOL(1 << 1, "HighSchool"),
    GRADUATE(1 << 2, "Graduate"),
    MASTER(1 << 3, "Master"),
    PHD(1 << 4, "Phd");

    final int val;
    final String content;

    Level(int val, String content) {
        this.val = val;
        this.content = content;
    }
}
