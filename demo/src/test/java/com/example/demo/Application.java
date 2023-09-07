package com.example.demo;


import java.util.*;

/**
 * @author xhliu2
 **/
public class Application {

    private final static long UP = (long) 1e6;

    private int total, c1, c2;
    private final Set<Long> set = new HashSet<>();

    int[] cache;

    public long waysToBuyPensPencils(int _total, int cost1, int cost2) {
        total = _total;
        c1 = cost1;
        c2 = cost2;
        cache = new int[total + 1];
        Arrays.fill(cache, -1);
        return dfs(0, 0, 0);
    }

    int dfs(int cur, int cnt1, int cnt2) {
        long hash = cnt1 * UP + cnt2;
        if (set.contains(hash)) {
            return 0;
        }
        if (cur > total) return 0;
        System.out.println("cur=" + cur + "\tcnt1=" + cnt1 + "\tcnt2=" + cnt2);
        set.add(hash);
//        if (cache[cur] >= 0) {
//            return cache[cur];
//        }
        int ans = 1; // 两者都不购买
        ans += dfs(cur + c1, cnt1 + 1, cnt2); // 购买一支钢笔
        ans += dfs(cur + c2, cnt1, cnt2 + 1); // 购买一支铅笔
        cache[cur] = ans;
        return ans;
    }

    public static void main(String[] args) {
        Application app = new Application();
        System.out.println(app.waysToBuyPensPencils(100, 1, 1));
    }
}
