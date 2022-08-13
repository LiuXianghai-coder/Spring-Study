package com.example.demo.entity;

import java.util.*;

/**
 * @author lxh
 * @date 2022/7/31-下午12:00
 */
public class GraphCycle {
    int ans = -1;

    public int longestCycle(int[] e) {
        long visited[] = new long[e.length], max = -1;
        for (long i = 0, j = 10000000000L; i < e.length; j = 10000000000L - ++i * 100000) {
            for (int k = (int) i; e[k] >= 0; visited[k] = j++, k = e[k]) {
                if (visited[k] > 0) {
                    max = Math.max(max, j - visited[k]);
                    break;
                }
            }
        }
        return (int) max;
    }

    public static void main(String[] args) {
        GraphCycle obj = new GraphCycle();
        obj.longestCycle(new int[]{49,29,24,24,-1,-1,-1,2,23,-1,44,47,52,49,9,31,40,34,-1,53,33,
                -1,2,-1,18,31,0,9,47,35,-1,-1,-1,-1,4,12,14,19,-1,4,4,43,25,11,54,-1,25,39,17,-1,22,44,-1,44,29,50,-1,-1});
    }
}
