package com.example.demo.inter;

/**
 * @author xhliu2
 * @create 2021-10-19 9:36
 **/
public class TireTree {
    private final static int R = 26;

    private static class Node {
        private final Node[] next;
        private boolean isWord;

        Node () {
            this.next = new Node[R];
            this.isWord = false;
        }
    }

    private final Node root;

    public TireTree() {
        root = new Node();
    }

    public void addWord(String word) {
        final char[] array = word.toCharArray();
        Node p = root;
        for (int i = 0; i < array.length; ++i) {
            int ch = array[i] - 'a';
            if (p.next[ch] == null)
                p.next[ch] = new Node();
            p = p.next[ch];
        }

        p.isWord = true;
    }

    public boolean search(String word) {
        final char[] array = word.toCharArray();
        return search(root, array, 0);
    }

    private boolean search(Node root, char[] array, int index) {
        if (root == null)          return false;
        if (index == array.length) return false;

        char ch = array[index];
        if (ch != '.') {
            if (index == array.length - 1 && root.isWord)
                return true;

            return search(root.next[ch - 'a'], array, index + 1);
        }

        boolean ans = false;
        for (int i = 0; i < R; ++i) {
            if (index == array.length - 1 && root.isWord)
                return true;

            ans |= search(root.next[i], array, index + 1);
        }

        return ans;
    }

    public static void main(String[] args) {
        TireTree tree = new TireTree();

        tree.addWord("bad");
        tree.addWord("dad");
        tree.addWord("mad");
//        tree.addWord("a");

        System.out.println(tree.search("bad"));
    }
}
