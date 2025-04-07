package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author xhliu2
 **/
public class Application {

    public boolean canPartition(int[] nums) {
        int sum = 0, n = nums.length;
        for (int v : nums) {
            sum += v;
        }
        if (sum % 2 != 0) {
            return false;
        }

        int s = sum / 2;
        boolean[] f = new boolean[s + 1];
        f[0] = true;
        for (int x: nums) {
            for (int j = s; j >= x; --j) {
                f[j] = f[j - x] || f[j];
            }
            if (f[s]) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) throws Throwable {
        Application app = new Application();
        System.out.println(app.canPartition(new int[]{2, 2, 3, 5}));
    }
}
