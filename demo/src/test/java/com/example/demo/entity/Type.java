package com.example.demo.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

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
        String str = "data.proj.data";
        StringBuilder sb = new StringBuilder();
        char[] arr = str.toCharArray();

        boolean state = false;
        for (char c : arr) {
            if (state && c != '.') continue;

            if (c == '.') {
                sb.append(c);
                state = false;
            } else if (c == '#') state = true;
            else sb.append(c);
        }

        System.out.println(sb);
    }
}
