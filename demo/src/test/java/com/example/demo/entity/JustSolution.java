package com.example.demo.entity;

import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author lxh
 * @date 2022/7/17-上午9:47
 */
public class JustSolution {

    String[] nums;

    public int[] smallestTrimmedNumbers(String[] _nums, int[][] que) {
        int sz = que.length;
        int[] ans = new int[sz];
        nums = _nums;
        for (int i = 0; i < sz; ++i) {
            int tmp = cal(que[i]);
            ans[i] = tmp;
        }
        return ans;
    }

    int cal(int[] q) {
        List<String[]> list = new ArrayList<>();

        int sz = nums.length;
        for (int i = 0; i < sz; ++i) {
            String str = nums[i];
            String tmp = str.substring(str.length() - q[1]);
            list.add(new String[]{tmp, String.valueOf(i)});
        }
        list.sort((a, b) -> {
            int t1 = Integer.parseInt(a[1]);
            int t2 = Integer.parseInt(b[1]);
            return !a[0].equals(b[0]) ? a[0].compareTo(b[0]) : t1 - t2;
        });

        return Integer.parseInt(list.get(q[0] - 1)[1]);
    }

    public int minOperations(int[] nums, int[] divides) {
        TreeMap<Integer, Integer> ts = new TreeMap<>();
        for (int val : nums) {
            ts.put(val, ts.getOrDefault(val, 0) + 1);
        }

        int gcd = gcd(divides);
        int ans = 0;
        System.out.println("gcd=" + gcd);
        for (Integer key : ts.keySet()) {
            if (gcd % key == 0) {
                return ans;
            }
            ans += ts.get(key);
        }
        return -1;
    }

    int gcd(int[] nums) {
        int ans = nums[0];
        for (int i = 1; i < nums.length; ++i) {
            ans = BigInteger.valueOf(nums[i]).gcd(BigInteger.valueOf(ans)).intValue();
        }
        return ans;
    }

    static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    public static long zeroFilledSubarray(int[] nums) {
        int n = nums.length;
        long ans = 0L;
        int lo = 0, hi = 0;
        while (hi < n) {
            lo = hi;
            while (hi < n && nums[hi] != 0) hi++;
            while (hi < n && nums[hi] == 0) hi++;
            while (lo < hi && nums[lo] != 0) lo++;

            int len = hi - lo;
            ans += (long) (len + 1) * len / 2;
        }

        return ans;
    }

    public static void main(String[] args) {
        BigInteger res = BigInteger.ONE;
        Random random = ThreadLocalRandom.current();
        BigInteger p = BigInteger.probablePrime(16, random);
        long start, end;
        long ans = 1;
        start = System.currentTimeMillis();
        for (int i = 0; i < 1000_000; ++i) {
            ans = ans * p.longValue();
        }
        end = System.currentTimeMillis();
        System.out.printf("take time %d ms\n", (end - start));
    }
}
