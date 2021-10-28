package com.example.demo;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author xhliu2
 * @create 2021-10-28 10:31
 **/
class Solution {
    static Set<Integer> set = new HashSet<>();
    static {
        for (int i = 1; i < (int)1e9+10; i *= 2) set.add(i);
    }
    public boolean reorderedPowerOf2(int n) {
        int[] cnts = new int[10];
        while (n != 0) {
            cnts[n % 10]++;
            n /= 10;
        }
        int[] cur = new int[10];
        out:for (int x : set) {
            Arrays.fill(cur, 0);
            while (x != 0) {
                cur[x % 10]++;
                x /= 10;
            }
            for (int i = 0; i < 10; i++) {
                if (cur[i] != cnts[i]) continue out;
            }
            return true;
        }
        return false;
    }
}
