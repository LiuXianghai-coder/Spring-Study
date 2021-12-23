package org.xhliu.springmvc;

import org.xhliu.springmvc.entity.SuffixArrayX;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    String text;
    int n;

    String lcp(int _s1, int _s2) {
        int a = Math.min(_s1, _s2), b = Math.max(_s1, _s2);

        for (int i = a, j = b; i < n && j < n; ++i, ++j)
            if (text.charAt(i) != text.charAt(j))
                return text.substring(a, i);

        return text.substring(b, n);
    }

    public String longestDupSubstring(String s) {
        text = s;

        n = s.length();
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < n; ++i) list.add(i);

        list.sort((a, b) -> {
            for (;a < n && b < n; ++a, ++b) {
                if (text.charAt(a) != text.charAt(b))
                    return text.charAt(a) - text.charAt(b);
            }
            return a < n ? 1 : -1;
        });

        String ans = "";

        int size = list.size();
        for (int i = 0, j = 1; j < size; ++i, ++j) {
            String t = lcp(list.get(i), list.get(j));
            if (t.length() > ans.length()) ans = t;
        }

        return  ans;
    }

    public static String lrs(String text) {
        int n = text.length();
        SuffixArrayX sa = new SuffixArrayX(text);
        String lrs = "";
        long start, end;
        start = System.currentTimeMillis();
        for (int i = 1; i < n; i++) {
            int length = sa.lcp(i);
            if (length > lrs.length()) {
                // lrs = sa.select(i).substring(0, length);
                lrs = text.substring(sa.index(i), sa.index(i) + length);
            }
        }
        end = System.currentTimeMillis();
        System.out.printf("Search Take Time = %d ms\n", (end - start));
        return lrs;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        String str = "aaaaaaa";

        System.out.println("str length= " + str.length());
        long start, end;
        start = System.currentTimeMillis();
        lrs(str);
//        solution.longestDupSubstring(str);
        end = System.currentTimeMillis();
        System.out.printf("Take Time %d ms\n", (end - start));
//        System.out.printf("%s\n", solution.longestDupSubstring(str));
    }
}
