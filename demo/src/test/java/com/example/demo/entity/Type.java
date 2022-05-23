package com.example.demo.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

    static class ObjectNode {
        List<String> idList;

        public List<String> getIdList() {
            return idList;
        }

        public void setIdList(List<String> idList) {
            this.idList = idList;
        }

        @Override
        public String toString() {
            return "ObjectNode{" +
                    "list=" + idList +
                    '}';
        }
    }

    public static void main(String[] args) {
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<>();
        System.out.println(gson.toJson(map));
    }
}
