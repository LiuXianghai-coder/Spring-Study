package com.example.demo.entity;

/**
 * @author xhliu2
 * @create 2021-09-16 13:03
 **/
public class TireTree {
    private static class Node {
        private final boolean[] valid   =   new boolean[27];
        private final boolean[] isWord  =   new boolean[27];
        private final Node[]    next    =   new Node[27];
    }

    private final Node root;

    public TireTree() {root = new Node();}

    public void add(String word) {
        if (0 == word.length())
            return;

        char[] wordArray = word.toCharArray();
        add(root, wordArray, 0);
    }

    private void add(Node root, char[] wordArray, final int index) {
        final int ch = wordArray[index] - 'a';
        root.valid[ch] = Boolean.TRUE;

        if (index == wordArray.length - 1) {
            root.isWord[ch] = Boolean.TRUE;
            return;
        }

        if (root.next[ch] == null)
            root.next[ch] = new Node();

        add(root.next[ch], wordArray, index + 1);
    }

    public boolean search(String word) {
        if (0 == word.length())
            return false;

        char[] wordArray = word.toCharArray();

        return search(root, wordArray, 0);
    }

    private boolean search(Node root, char[] wordArray, final int index) {
        if (null == root) return false;

        int ch = wordArray[index] - 'a';
        if (index == wordArray.length - 1)
            return root.isWord[ch];

        if (!root.valid[ch])
            return false;

        return search(root.next[ch], wordArray, index + 1);
    }

    public boolean isValid(String word) {
        char[] wordArray = word.toCharArray();
        return isValid(root, wordArray, 0);
    }

    private boolean isValid(Node root, char[] wordArray, final int index) {
        int ch = wordArray[index] - 'a';
        if (index == wordArray.length - 1)
            return true;

        if (!root.valid[ch])
            return false;
        return isValid(root.next[ch], wordArray, index + 1);
    }

    public static void main(String[] args) {
        TireTree tireTree = new TireTree();
        tireTree.add("aba");
        tireTree.add("baa");
        tireTree.add("bab");
        tireTree.add("aaab");
        tireTree.add("aaa");
        tireTree.add("aaaa");
        tireTree.add("aaba");

        System.out.println(tireTree.search("aaab"));
    }
}
