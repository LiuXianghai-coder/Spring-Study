package com.example.demo;

import java.util.*;

/**
 * @author xhliu2
 **/
public class Application {

    static long pow(long a, int b, int mod) {
        long ans = 1L;
        long t = a;
        for (int i = 0; i < 31; ++i) {
            if (((b >> i) & 1) != 0) {
                ans *= t;
                ans %= mod;
            }

            t *= t;
            t %= mod;
        }
        return ans;
    }

    public static void main(String[] args) {
//        System.out.println(pow(pow(2, 6, 10), 4, 6));
        System.out.println(pow(2, 2, 100000000));
        System.out.println(pow(2, 1, 100000000));
        System.out.println(pow(2, 4, 100000000));
        System.out.println(pow(2, 5, 100000000));
        System.out.println(pow(2, 6, 100000000));
    }
}
