package com.example.demo.entity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xhliu
 * @create 2022-03-28-16:37
 **/
public class Plain {
    static class Data {
        String title;
        String key;
    }

    private List<Data> map;

    public List<Data> getMap() {
        return map;
    }

    public void setMap(List<Data> map) {
        this.map = map;
    }

    public static void main(String[] args) {
        Plain plain = new Plain();
        List<Data> list = new ArrayList<>();
        Data data = new Data();
        data.key = "123";
        data.title = "123";
        list.add(data);
        list.add(new Data());
        plain.setMap(list);

        Gson gson = new GsonBuilder().create();

        System.out.println(gson.toJson(plain));
        System.out.println(-4 % 2);
    }
}
