package com.example.demo.app;

import com.fasterxml.jackson.core.type.TypeReference;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

/**
 * @author xhliu2
 * @create 2021-09-06 16:19
 **/
public class Application {

    int[] nums;
    long[] prefix;
    int[][] cache;

    int n, k;

    public static void main(String[] args) {
        List<String> list = List.of("1", "2", "3", "4");
        Type[] types = list.getClass().getGenericInterfaces();
        new TypeReference<List<String>>() {
        };

    }

    public int splitArray(int[] nums, int k) {
        this.nums = nums;
        this.n = nums.length;
        this.k = k;
        this.prefix = new long[n + 1];
        this.cache = new int[n + 1][k + 1];
        for (int[] arr : cache) {
            Arrays.fill(arr, -1);
        }
        for (int i = 1; i <= n; ++i) {
            prefix[i] = prefix[i - 1] + nums[i - 1];
        }
        return dfs(n, k);
    }

    int dfs(int i, int j) {
        if (j == 1) {
            return (int) prefix[i];
        }
        if (i < 1) return 0;
        if (cache[i][j] != -1) return cache[i][j];
        int ans = Integer.MAX_VALUE;
        for (int m = i - 1; m > 1; --m) {
            ans = Math.min(ans, Math.max(dfs(m, j - 1), (int) (prefix[i] - prefix[m])));
        }
        cache[i][j] = ans;
        return ans;
    }
}
