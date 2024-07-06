package com.example.demo;

import java.util.Arrays;

/**
 * @author xhliu2
 **/
public class Application {

    static final int[] map = new int[]{0, 0, 1, -1, -1, 1, 1, -1, 0, 1};

    int[][] cache;

    public static void main(String[] args) {
        Application app = new Application();
        System.out.println(app.rotatedDigits(10));
    }

    public int rotatedDigits(int n) {
        char[] arr = String.valueOf(n).toCharArray();
        cache = new int[arr.length + 1][10];
        for (int[] intx : cache) Arrays.fill(intx, -1);
        return dp(arr, 0, 0, false, true);
    }

    int dp(char[] arr, int index, int cnt, boolean filled, boolean limited) {
        if (index >= arr.length) {
            return cnt > 0 ? 1 : 0;
        }
        if (!limited && cache[index][cnt] >= 0) {
            return cache[index][cnt];
        }
        int ans = 0;
        if (!limited) {
            ans += dp(arr, index + 1, 0, false, false);
        }
        int up = limited ? arr[index] - '0' : 9;
        for (int i = filled ? 0 : 1; i <= up; ++i) {
            if (map[i] < 0) continue;
            ans += dp(arr, index + 1, cnt + map[i], true, limited && up == i);
        }
        cache[index][cnt] = ans;
        return ans;
    }
}
