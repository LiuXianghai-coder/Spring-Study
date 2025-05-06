package com.example.demo;

import org.junit.jupiter.api.Assertions;

import java.util.*;

/**
 * @author xhliu2
 **/
public class Application {

    static final long MOD = (int) 1e9 + 7;

    public int numTilings(int n) {
        long[][] dp = new long[n + 1][4];
        dp[0][3] = 1L;
        for (int i = 1; i <= n; ++i) {
            dp[i][0] = dp[i - 1][3];
            dp[i][1] = (dp[i - 1][0] + dp[i - 1][2]) % MOD;
            dp[i][2] = (dp[i - 1][0] + dp[i - 1][1]) % MOD;
            dp[i][3] = (dp[i - 1][0] + dp[i - 1][1] + dp[i - 1][2] + dp[i - 1][3]) % MOD;
        }
        return (int) (dp[n][3] % MOD);
    }

    public static void main(String[] args) throws Throwable {
        Application app = new Application();
    }
}
