package com.example.demo.entity;

/**
 * @author xhliu
 * @create 2022-07-12-9:14
 **/
public class MagicDictionary {

    private static class Node {
        final boolean[] valid = new boolean[26];
        final boolean[] finished = new boolean[26];
        final Node[] next = new Node[26];
    }

    private final Node root;

    public MagicDictionary() {
        root = new Node();
    }

    public void buildDict(String[] dictionary) {
        for (String word : dictionary) add(word);
    }

    void add(String word) {
        if (0 == word.length()) return;
        add(root, word.toCharArray(), 0);
    }

    void add(Node node, char[] arr, int idx) {
        int ch = arr[idx] - 'a';
        node.valid[ch] = true;

        if (idx == arr.length - 1) {
            node.finished[ch] = true;
            return;
        }

        if (node.next[ch] == null) node.next[ch] = new Node();
        add(node.next[ch], arr, idx + 1);
    }

    boolean query(Node node, String s, int idx, int limit) {
        if (limit < 0) return false;

        int ch = s.charAt(idx) - 'a';
        if (idx == s.length() - 1) {
            for (int i = 0; i < 26; ++i) {
                if (!node.valid[i] || !node.finished[i]) continue;
                if (ch == i && limit == 0) return true;
                if (ch != i && limit == 1) return true;
            }
            return false;
        }

        for (int i = 0; i < 26; ++i) {
            if (!node.valid[i]) continue;
            if (query(node.next[i], s, idx + 1, i == ch ? limit : limit - 1))
                return true;
        }
        return false;
    }

    public boolean search(String word) {
        return query(root, word, 0, 1);
    }

    public static void main(String[] args) {
        MagicDictionary dict = new MagicDictionary();
        dict.buildDict(new String[]{"hello","hallo","leetcode"});
        System.out.println(dict.search("leetcodd"));
    }
}
