package com.example.demo.algorithm;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author lxh
 */
public class RabinKarpSearch {

    public RabinKarpSearch() {
    }

    public List<Integer> findSubIndexList(String text, String pat) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        final long R = BigInteger.probablePrime(16, random).longValue();
        final long MOD = BigInteger.probablePrime(31, random).longValue();
        List<Integer> ans = new ArrayList<>();

        int n = text.length(), m = pat.length();
        long[] ha = new long[n + 1], p = new long[n + 1];
        p[0] = 1;
        for (int i = 1; i <= n; ++i) {
            char ch = text.charAt(i - 1);
            p[i] = (p[i - 1] * R % MOD) % MOD;
            ha[i] = (ha[i - 1] * R % MOD + ch) % MOD;
        }
        long t = 0L;
        for (int i = 1; i <= m; ++i) {
            t = (t * R % MOD + pat.charAt(i - 1)) % MOD;
        }
        for (int i = 1, j = m; j <= n; ++j, ++i) {
            long hash = (ha[j] - ha[i - 1] * p[j - i + 1] % MOD + MOD) % MOD;
            if (hash == t) ans.add(i - 1);
        }
        return ans;
    }

    public static void main(String[] args) {
        RabinKarpSearch search = new RabinKarpSearch();
        System.out.println(search.findSubIndexList("lagopphhnl", "gopph"));
        System.out.println(search.findSubIndexList("abacadabrabracabracadabrabrabracad", "abracadabra"));
        System.out.println(search.findSubIndexList("abacadabrabracabracadabrabrabracad", "rab"));
    }
}
