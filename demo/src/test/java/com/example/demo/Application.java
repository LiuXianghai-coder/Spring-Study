package com.example.demo;


import java.util.*;

/**
 * @author xhliu2
 **/
public class Application {

    static final int mod = (int) 1e9 + 7;

    public int maximumXorProduct(long a, long b, int n) {
        long p = a, q = b;
        for (int i = n - 1; i >= 0; --i) {
            long c1 = (a >> i) & 1;
            long c2 = (b >> i) & 1;
            if (c1 == c2) {
                p |= 1L << i;
                q |= 1L << i;
            } else if (p < q) {
                p |= 1L << i;
            } else {
                q |= 1L << i;
            }
        }
        q %= mod;
        p %= mod;
        return (int) (p * q) % mod;
    }

    public static void main(String[] args) {
        System.out.println(UUID.randomUUID().toString().replaceAll("-", ""));
        System.out.println(UUID.randomUUID().toString().replaceAll("-", ""));
        System.out.println(UUID.randomUUID().toString().replaceAll("-", ""));
    }
}
