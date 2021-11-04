package com.example.demo;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author xhliu2
 * @create 2021-10-28 10:31
 **/
class Solution {
    public static int findTargetSumWays(int[] nums, int target) {
        int n = nums.length;
        int[][] dp = new int[n][3001];

        dp[0][nums[0] + 1000] = 1;
        dp[0][-nums[0] + 1000] = 1;
        for (int i = 1; i < n; ++i) {
            for (int j = 0; j <= 2000; ++j) {
                dp[i][j] += dp[i - 1][j + nums[i]];
                if (j >= nums[i]) {
                    dp[i][j] += dp[i - 1][j - nums[i]];
                }

                // if (dp[i][j] != 0) {
                //     System.out.println(dp[i][j] + "\ti=" + i + "\tj=" + j);
                // }
            }
        }

        return dp[n - 1][target + 1000];
    }

    public static void main(String[] args) {
        System.out.println(findTargetSumWays(new int[]{0,0,0,0,0,0,0,0,1}, 1
        ));
    }
}
