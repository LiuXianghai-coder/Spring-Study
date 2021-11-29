package org.xhliu.springwebflux.demo;

import java.util.HashMap;
import java.util.Map;

public class Demo {
    public int findNthDigit(int n) {
        // 9+2*90+3*900+4*9000+5*90000+6*900000+7*9000000+8*90000000+9*900000000
        long[] bits = new long[]{0, 9, 2*90, 3*900, 4*9000, 5*90000, 6 * 900000, 7 * 9000000, 8*90000000, 9L *900000000};
        int n = bits.length;
        long[] prefix = new long[n];
        prefix[0] = 0;
        for (int i = 1; i < n; ++i)
            prefix[i] = prefix[i - 1] + bits[i];

        int idx = 0;
        for (int i = 0; i < n; ++i) {
            if (n < bits[i]) {
                idx = i;
                break;
            }
        }

        int tmp = n;
        tmp -= prefix[i - 1];
        int num = tmp / 2;

        return 2;
    }

    public static void main(String[] args) {
        Map<Integer, Integer> map = new HashMap<>();

        map.put(10, 1);
        map.put(9, 1);
        map.put(5, 1);
        map.put(4, 1);
        map.put(6, 1);
        map.put(2, 1);

        for (Integer key : map.keySet()) {
            System.out.println(key);
        }
    }
}
