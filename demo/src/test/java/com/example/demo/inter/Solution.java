package com.example.demo.inter;

import java.util.Arrays;

/**
 * @author xhliu
 * @create 2022-06-01-8:33
 **/
public class Solution {
    int[] sticks;
    int n, visit;

    public boolean makesquare(int[] _sticks) {
        sticks = _sticks; n = sticks.length;
        int[] edges = new int[4];
        return backTrace(edges, 0, 0);
    }

    boolean backTrace(int[] edges, int cur, int take) {
        if (cur >= 4) {
            if (take >= n && check(edges)) {
                System.out.println(Arrays.toString(edges));
                return true;
            }
            return false;
        }

        boolean ans;
        for (int i = 0; i < n; ++i) {
            if ((visit & (1 << i)) != 0) continue;

            for (int j = 0; j < 4; ++j) {
                visit |= 1 << i;

                edges[j] += sticks[i];
                ans = backTrace(edges, cur + 1, take + 1);
                if (ans) return true;

                visit ^= 1 << i;
                edges[cur] -= sticks[i];
                ans = backTrace(edges, cur + 1, take);
                if (ans) return true;
            }
        }

        return false;
    }

    boolean check(int[] edges) {
        int val = edges[0];
        for (int i = 1; i < edges.length; ++i)
            if (edges[i] != val) return false;
        return true;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.makesquare(new int[]{1, 1, 2, 2, 2}));
    }
}
