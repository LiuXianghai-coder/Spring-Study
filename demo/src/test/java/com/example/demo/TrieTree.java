package com.example.demo;

import org.apache.ibatis.io.Resources;
import org.junit.Assert;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * @author lxh
 */
public class TrieTree<V> {

    private final static int R = 256;

    private Node root;

    private int n;

    static class Node {
        private Object val;
        private final Node[] next = new Node[R];
    }

    public TrieTree() {
    }

    public void put(String key, V val) {
        if (key == null) {
            throw new IllegalArgumentException("first argument to put() is null");
        }
        if (val == null) delete(key);
        else root = put(root, key, val, 0);
    }

    private Node put(Node x, String key, V val, int index) {
        if (x == null) x = new Node();
        if (index == key.length()) {
            if (x.val == null) n++;
            x.val = val;
            return x;
        }
        char ch = key.charAt(index);
        x.next[ch] = put(x.next[ch], key, val, index + 1);
        return x;
    }

    public void delete(String key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to delete() is null");
        }
        root = delete(root, key, 0);
    }

    private Node delete(Node x, String key, int index) {
        if (x == null) return null;
        if (index == key.length()) {
            if (x.val != null) n--;
            x.val = null;
        } else {
            char ch = key.charAt(index);
            x.next[ch] = delete(x.next[ch], key, index + 1);
        }
        if (x.val != null) return x;
        for (int c = 0; c < R; ++c) {
            if (x.next[c] != null) {
                return x;
            }
        }
        return null;
    }

    public Iterable<String> keys() {
        return keysWithPrefix("");
    }

    public Iterable<String> keysWithPrefix(String prefix) {
        Deque<String> results = new ArrayDeque<>();
        Node x = get(root, prefix, 0);
        collect(x, new StringBuilder(prefix), results);
        return results;
    }

    private void collect(Node x, StringBuilder prefix, Deque<String> results) {
        if (x == null) return;
        if (x.val != null) results.offer(prefix.toString());
        for (char c = 0; c < R; c++) {
            prefix.append(c);
            collect(x.next[c], prefix, results);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }

    public int size() {
        return n;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    @SuppressWarnings("unchecked")
    public V get(String key) {
        if (key == null) throw new IllegalArgumentException("argument to get() is null");
        Node x = get(root, key, 0);
        if (x == null) return null;
        return (V) x.val;
    }

    private Node get(Node root, String key, int index) {
        if (root == null) return null;
        if (index == key.length()) return root;
        char ch = key.charAt(index);
        return get(root.next[ch], key, index + 1);
    }

    public boolean contains(String key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    }

    public static void main(String[] args) {
        TrieTree<Integer> trieTree = new TrieTree<>();
        Set<String> wordSet = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(Resources.getResourceAsReader("words.txt"))) {
            String word;
            while ((word = reader.readLine()) != null) {
                wordSet.add(word);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int i = 0;
        for (String word : wordSet) {
            trieTree.put(word, i++);
        }

        for (String key : trieTree.keys()) {
            Assert.assertTrue(wordSet.contains(key));
        }
    }
}
