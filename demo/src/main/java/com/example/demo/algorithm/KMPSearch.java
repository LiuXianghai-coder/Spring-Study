package com.example.demo.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * 目前已知的两种 KMP 算法具体实现：
 * <ol>
 *     <li>
 *         基于 DFA 的搜索实现: 通过预先构建待搜索的模式子串对应的 DFA，模拟在匹配失败时应当回退的位置，
 *         从而避免每次失败匹配是都需要后退到初始位置.
 *         具体详情可以参考：<a href="https://algs4.cs.princeton.edu/53substring/KMP.java.html">https://algs4.cs.princeton.edu/53substring/KMP.java.html</a>
 *     </li>
 *     <li>
 *         基于前缀和后缀的部分匹配表，这种方式通过前缀和后缀的公共部分来确定模式子串需要回退的位置，这种方式相较于
 *         DFA 的实现来讲极大地降低了所需的额外空间，具体的解释可以参考：
 *         <a href="https://www.zhihu.com/question/21923021/answer/37475572">https://www.zhihu.com/question/21923021/answer/37475572</a>
 *     </li>
 * </ol>
 *
 * @author lxh
 */
public class KMPSearch {

    private final int R; // 字符集的数量大小

    public KMPSearch(int R) {
        this.R = R;
    }

    public KMPSearch() {
        this.R = 256;
    }

    public List<Integer> dfaFindSubStrIndexList(String text, String pattern) {
        checkInput(text, pattern);
        int[][] dfa = buildPatDfa(pattern);
        int n = text.length();
        List<Integer> ans = new ArrayList<>();
        for (int i = 0, j = 0; i < n; ++i) {
            char ch = text.charAt(i);
            j = dfa[j][ch];
            if (j == pattern.length()) {
                ans.add(i - pattern.length() + 1);
                j = dfa[j][ch]; // 重新回退到继续搜索的状态
            }
        }
        return ans;
    }

    public List<Integer> findSubStrIndeList(String text, String pattern) {
        checkInput(text, pattern);
        int[] next = calculateNext(pattern);
        int cnt = 0; // 已经匹配的字符数
        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < text.length(); ++i) {
            while (cnt > 0 && text.charAt(i) != pattern.charAt(cnt)) {
                cnt = next[cnt - 1];
            }
            if (text.charAt(i) == pattern.charAt(cnt)) {
                cnt++;
            }
            if (cnt == pattern.length()) {
                ans.add(i - pattern.length() + 1);
                cnt = next[cnt - 1];
            }
        }
        return ans;
    }

    private int[] calculateNext(String pattern) {
        int n = pattern.length();
        int[] next = new int[n];
        int maxLength = 0;
        for (int i = 1; i < n; ++i) {
            // 前缀和后缀的公共部分长度计算，当发生不匹配时可以共这部分开始继续进行查找，而不需要回退整个子串
            while (maxLength > 0 && pattern.charAt(maxLength) != pattern.charAt(i)) {
                maxLength = next[maxLength - 1];
            }
            if (pattern.charAt(maxLength) == pattern.charAt(i)) {
                maxLength++;
            }
            next[i] = maxLength;
        }
        return next;
    }

    private int[][] buildPatDfa(String pattern) {
        int n = pattern.length();
        int[][] dfa = new int[n + 1][R];
        dfa[0][pattern.charAt(0)] = 1;
        for (int x = 0, i = 1; i <= n; ++i) {
            System.arraycopy(dfa[x], 0, dfa[i], 0, R);
            if (i >= n) break; // 当完全匹配时，需要发生的状态转换
            dfa[i][pattern.charAt(i)] = i + 1; // 此时已经匹配成功，进入下一个字符匹配
            x = dfa[x][pattern.charAt(i)]; // 当发生不匹配时，需要重启的状态至
        }
        return dfa;
    }

    private void checkInput(String text, String pattern) {
        if (text == null) {
            throw new IllegalArgumentException("非法的待搜索文本");
        }
        if (pattern == null || pattern.trim().isEmpty()) {
            throw new IllegalArgumentException("非法的模式子串");
        }
    }

    public static void main(String[] args) {
        KMPSearch search = new KMPSearch();

        System.out.println("==== Test-1 ===");
        String text1 = "BCBAABACAABABACAA";
        String pat1 = "ABABAC";
        System.out.println(search.dfaFindSubStrIndexList(text1, pat1));
        System.out.println(search.findSubStrIndeList(text1, pat1));

        System.out.println("==== Test-2 ===");
        String text2 = "ccaabaabaabaaabaab";
        String pat2 = "aabaaaba";
        System.out.println(search.dfaFindSubStrIndexList(text2, pat2));
        System.out.println(search.findSubStrIndeList(text2, pat2));

        System.out.println("==== Test-3 ===");
        String text3 = "ccaabaabaabaaabaab";
        String pat3 = "aabaaabb";
        System.out.println(search.dfaFindSubStrIndexList(text3, pat3));
        System.out.println(search.findSubStrIndeList(text3, pat3));
    }
}
