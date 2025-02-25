package com.example.demo.algorithm;

import cn.hutool.core.io.FileUtil;
import com.alibaba.druid.util.StringUtils;
import org.junit.jupiter.api.Assertions;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.TreeMap;

/**
 *@author lxh
 */
public class TreeSkiplist extends Skiplist {

    private TreeMap<Integer, Integer> countMap = new TreeMap<>();

    public TreeSkiplist() {
        super();
    }

    public boolean search(int target) {
        return countMap.containsKey(target);
    }

    public void add(int num) {
        countMap.put(num, countMap.getOrDefault(num, 0) + 1);
    }

    public boolean erase(int num) {
        if (!countMap.containsKey(num)) {
            return false;
        }
        int count = countMap.get(num);
        if (count > 1) {
            countMap.put(num, count - 1);
        } else {
            countMap.remove(num);
        }
        return true;
    }
}
