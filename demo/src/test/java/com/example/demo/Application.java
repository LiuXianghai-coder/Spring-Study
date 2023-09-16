package com.example.demo;


import java.util.*;

/**
 * @author xhliu2
 **/
public class Application {

    static class Node {
        boolean finished = false;
        TreeMap<Character, Node> next = new TreeMap<>();
    }

    void add(String word, int index, Node root) {
        if (index == word.length()) {
            root.finished = true;
            return;
        }
        char ch = word.charAt(index);
        if (root.next.get(ch) == null) {
            root.next.put(ch, new Node());
        }
        add(word, index + 1, root.next.get(ch));
    }

    List<String> query(String word, Node root, List<String> list) {
        char[] arr = new char[2048];
        for (int i = 0; i < word.length(); ++i) {
            char ch = word.charAt(i);
            root = root.next.get(ch);
            arr[i] = ch;
            if (root == null) return list;
        }
        return query(root, arr, word.length(), list);
    }

    List<String> query(Node root, char[] arr, int len, List<String> list) {
        if (root == null) return list;
        if (list.size() >= 3) return list;
        if (root.finished) {
            list.add(new String(arr, 0, len));
        }
        if (list.size() >= 3) return list;
        for (Map.Entry<Character, Node> entry : root.next.entrySet()) {
            arr[len] = entry.getKey();
            query(entry.getValue(), arr, len + 1, list);
        }
        return list;
    }

    final Node root = new Node();

    public List<List<String>> suggestedProducts(String[] products, String searchWord) {
        for (String prod : products) add(prod, 0, root);
        List<List<String>> ans = new ArrayList<>();
        for (int i = 0; i < searchWord.length(); ++i) {
            String sub = searchWord.substring(0, i + 1);
            ans.add(query(sub, root, new ArrayList<>()));
        }
        return ans;
    }

    public static void main(String[] args) {

    }
}
