package com.example.demo;


import org.apache.ibatis.io.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

/**
 * @author xhliu2
 **/
public class Application {

    static class Node {
        boolean finished = false;
        TreeMap<Character, Node> next = new TreeMap<>();
    }

    private Node root = new Node();

    public void add(String word) {
        add(root, word, 0);
    }

    private void add(Node root, String word, int index) {
        int n = word.length();
        if (index == n) {
            root.finished = true;
            return;
        }
        char ch = word.charAt(index);
        if (root.next.get(ch) == null) {
            root.next.put(ch, new Node());
        }
        add(root.next.get(ch), word, index + 1);
    }

    public List<String> search(String keyWord, int len) {
        List<String> ans = new ArrayList<>();
        search(root, keyWord, 0, len, ans, new StringBuilder());
        return ans;
    }

    private void search(Node root, String keyWord, int index, int len, List<String> container, StringBuilder sb) {
        if (index >= len) {
            if (root.finished) container.add(sb.toString());
            if (container.size() >= 3) return;
            for (Map.Entry<Character, Node> entry : root.next.entrySet()) {
                sb.append(entry.getKey());
                search(entry.getValue(), keyWord, index, len, container, sb);
                sb.deleteCharAt(sb.length() - 1);
                if (container.size() >= 3) return;
            }
            return;
        }
        char ch = keyWord.charAt(index);
        if (!root.next.containsKey(ch)) return;
        sb.append(ch);
        search(root.next.get(ch), keyWord, index + 1, len, container, sb);
        sb.deleteCharAt(sb.length() - 1);
    }

    public List<List<String>> suggestedProducts(String[] products, String searchWord) {
        for (String s : products) add(s);
        List<List<String>> ans = new ArrayList<>();
        for (int i = 1; i <= searchWord.length(); ++i) {
            ans.add(search(searchWord, i));
        }
        return ans;
    }

    public static void main(String[] args) {
        Application app = new Application();
        String[] prods = new String[]{"code", "codephone", "coddle", "coddles", "codes"};
        List<List<String>> lists = app.suggestedProducts(prods, "coddle");
        for (List<String> s : lists) {
            System.out.println(s);
        }
    }
}
