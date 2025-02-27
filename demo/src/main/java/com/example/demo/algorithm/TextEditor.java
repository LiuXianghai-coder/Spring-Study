package com.example.demo.algorithm;

import org.junit.jupiter.api.Assertions;

/**
 *@author lxh
 */
public class TextEditor {
    static class Node {
        Node prev, next;
        final char ch;

        public Node(char ch) {
            this.ch = ch;
        }
    }

    Node head, tail;
    Node index = null;

    public TextEditor() {
        this.head = new Node('#');
        this.tail = new Node('#');
        this.head.next = this.tail;
        this.tail.prev = this.head;
        this.index = head;
    }

    public void addText(String text) {
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            index = addCharacter(index, ch);
        }
    }

    private Node addCharacter(Node node, char ch) {
        Node curNode = new Node(ch);
        Node next = node.next;

        node.next = curNode;
        next.prev = curNode;

        curNode.prev = node;
        curNode.next = next;

        return curNode;
    }

    private Node deleteNode(Node node) {
        Node prev = node.prev;
        Node next = node.next;

        prev.next = next;
        next.prev = prev;
        return prev;
    }

    public int deleteText(int k) {
        int ans = 0;
        if (index == head) {
            return ans;
        }
        for (int i = 0; i < k; i++) {
            index = deleteNode(index);
            ans++;
            if (index == head) {
                return ans;
            }
        }
        return ans;
    }

    public String cursorLeft(int k) {
        if (index == head) {
            return "";
        }
        for (int i = 0; i < k; i++) {
            index = index.prev;
            if (index == head) {
                return "";
            }
        }
        return concatLeftStr(index);
    }

    public String cursorRight(int k) {
        for (int i = 0; i < k; i++) {
            if (index.next == tail) {
                return concatLeftStr(index);
            }
            index = index.next;
        }
        return concatLeftStr(index);
    }

    private String concatLeftStr(Node index) {
        StringBuilder sb = new StringBuilder();
        Node p = index;
        for (int i = 0; i < 10; i++) {
            if (p == head) {
                return sb.reverse().toString();
            }
            sb.append(p.ch);
            p = p.prev;

        }
        return sb.reverse().toString();
    }

    public static void main(String[] args) {
        TextEditor textEditor = new TextEditor(); // 当前 text 为 "|" 。（'|' 字符表示光标）
        textEditor.addText("jxarid"); // 当前文本为 "leetcode|" 。
        textEditor.cursorLeft(5);
        textEditor.cursorLeft(10);
        textEditor.addText("du");
        Assertions.assertEquals(2, textEditor.deleteText(20));
    }
}
