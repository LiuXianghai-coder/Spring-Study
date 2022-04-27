package com.example.demo.entity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author xhliu
 * @create 2022-04-06-11:06
 **/
public class PeriodInfo {
    private Type type;

    private String info;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public static void main(String[] args) {
        PeriodInfo info = new PeriodInfo();
        info.setType(Type.YEAR);
        info.setInfo("simple");

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println(gson.toJson(info));

        String str = "abcde";
        System.out.println(str.substring(0, 1));
    }
}
