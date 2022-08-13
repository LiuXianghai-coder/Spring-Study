package com.example.demo.tools;

import java.util.*;

/**
 * @author lxh
 * @date 2022/7/16-上午10:41
 */
public class WordFilter {
    private static class TrieNode {
        boolean end;
        TrieNode[] tns = new TrieNode[26];
        List<Integer> idx = new ArrayList<>();
    }

    void add(TrieNode node, char[] arr, int d, int idx) {
        node.idx.add(idx);
        if (d == arr.length) {
            node.end = true;
            return;
        }

        int ch = arr[d] - 'a';
        if (node.tns[ch] == null) node.tns[ch] = new TrieNode();
        add(node.tns[ch], arr, d + 1, idx);
    }

    int query(String a, String b) {
        char[] arr1 = a.toCharArray(), arr2 = b.toCharArray();
        TrieNode p1 = tr1, p2 = tr2;
        for (int i = 0; i < arr1.length; ++i) {
            int ch = arr1[i] - 'a';
            if (p1.tns[ch] == null) {
                // System.out.println("Hello World");
                return -1;
            }
            p1 = p1.tns[ch];
        }
        for (int i = 0; i < arr2.length; ++i) {
            int ch = arr2[i] - 'a';
            if (p2.tns[ch] == null) {
                System.out.println("Hello World " + arr2[i]);
                return -1;
            }
            p2 = p2.tns[ch];
        }
        List<Integer> l1 = p1.idx, l2 = p2.idx;
        for (int i = l1.size() - 1, j = l2.size() - 1; i >= 0 && j >= 0;) {
            int t1 = l1.get(i), t2 = l2.get(j);
            if (t1 == t2) return t1;
            if (t1 > t2) i--;
            else j--;
        }

        return -1;
    }

    private TrieNode tr1, tr2;

    public WordFilter(String[] words) {
        tr1 = new TrieNode();
        tr2 = new TrieNode();
        for (int i = 0; i < words.length; ++i) {
            StringBuilder sb = new StringBuilder(words[i]);
            add(tr1, sb.toString().toCharArray(), 0, i);
            add(tr2, sb.reverse().toString().toCharArray(), 0, i);
        }
    }

    public int f(String pref, String suff) {
        return query(pref, suff);
    }

    public static void main(String[] args) {
        WordFilter wf = new WordFilter(new String[]{"c", "i"});
        System.out.println(wf.query("c", "c"));
    }
}
