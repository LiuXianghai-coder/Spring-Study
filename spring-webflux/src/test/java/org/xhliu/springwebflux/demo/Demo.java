package org.xhliu.springwebflux.demo;

import java.util.HashMap;
import java.util.Map;

public class Demo {
    public static void main(String[] args) {
        Map<Integer, Integer> map = new HashMap<>();

        map.put(10, 1);
        map.put(9, 1);
        map.put(5, 1);
        map.put(4, 1);
        map.put(6, 1);
        map.put(2, 1);

        for (Integer key : map.keySet()) {
            System.out.println(key);
        }
    }
}
