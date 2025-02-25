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

    public static void main(String[] args) {
        String opList = FileUtil.readString(new File("/tmp/opList_1.txt"), StandardCharsets.UTF_8);
        String valList = FileUtil.readString(new File("/tmp/valList_1.txt"), StandardCharsets.UTF_8);
        String[] ops = opList.split(",");
        String[] vals = valList.split(",");
        Skiplist skiplist1 = new Skiplist();
        Skiplist skiplist2 = new TreeSkiplist();
        for (int i = 0; i < ops.length; i++) {
            String op = ops[i];
            int num = Integer.parseInt(vals[i].replaceAll("\\s+", "").replaceAll("\"", ""));
            skiplist1.check();
            if (StringUtils.equals("add", op)) {
                System.out.println("skiplist." + op + "(" + num + ");");
                skiplist1.add(num);
                skiplist2.add(num);
            } else if (StringUtils.equals("search", op)) {
                System.out.println("skiplist." + op + "(" + num + ");");
                boolean s1 = skiplist1.search(num);
                boolean s2 = skiplist2.search(num);
                if (s1 != s2) {
                    skiplist1.printArr();
                    skiplist1.search(num);
                }
                Assertions.assertEquals(s1, s2);
            } else if (StringUtils.equals("erase", op)) {
                System.out.println("skiplist." + op + "(" + num + ");");
                boolean e1 = skiplist1.erase(num);
                boolean e2 = skiplist2.erase(num);
                if (e1 != e2) {
                    skiplist1.erase(num);
                    skiplist1.search(num);
                }
                Assertions.assertEquals(e1, e2);
            }
        }
    }
}
