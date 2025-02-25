package com.example.demo.algorithm;

import cn.hutool.core.io.FileUtil;
import org.junit.jupiter.api.Assertions;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ThreadLocalRandom;

/**
 *@author lxh
 */
public class Skiplist {

    static class SkipNode {
        Node head, tail;

        @Override
        public String toString() {
            Node node = head;
            StringBuilder sb = new StringBuilder();
            while (node != null) {
                sb.append(node).append("\t");
                node = node.next;
            }
            sb.append("\n");
            return sb.toString();
        }

        public void check() {
            Node node = head.next;
            while (node != tail) {
                node.check();
                node = node.next;
            }
        }
    }

    static class Node {
        private final int val;
        private Node prev, next, nextLevel;

        Node(int val) {
            this.val = val;
        }

        public int getVal() {
            return val;
        }

        public void check() {
            if (val == Integer.MIN_VALUE) {
                throw new RuntimeException();
            }
            if (prev == null || next == null) {
                throw new RuntimeException();
            }
        }

        @Override
        public String toString() {
            return String.valueOf(val);
        }
    }

    private final static int MAX_LEVEL = 23;

    private final SkipNode[] arr = new SkipNode[MAX_LEVEL];

    private final ThreadLocalRandom random = ThreadLocalRandom.current();

    public Skiplist() {
        for (int i = 0; i < arr.length; i++) {
            this.arr[i] = new SkipNode();
            Node headerNode = new Node(Integer.MIN_VALUE);
            Node tailNode = new Node(Integer.MAX_VALUE);
            this.arr[i].head = headerNode;
            this.arr[i].tail = tailNode;
            this.arr[i].head.next = this.arr[i].tail;
            this.arr[i].tail.prev = this.arr[i].head;
        }
    }

    public boolean search(int target) {
        return search(null, 0, target);
    }

    private boolean search(Node parent, int level, int target) {
        if (level >= MAX_LEVEL) {
            return false;
        }

        Node searchNode = parent;
        if (searchNode == null) {
            SkipNode skipNode = this.arr[level];
            searchNode = skipNode.head;
        }

        while (searchNode.next.val <= target) {
            searchNode = searchNode.next;
        }
        if (searchNode.val == target) {
            return true;
        }
        return search(searchNode.nextLevel, level + 1, target);
    }

    public void add(int num) {
        add(null, 0, num);
    }

    private Node add(Node parent, int level, int num) {
        if (level >= MAX_LEVEL) {
            return null;
        }
        SkipNode skipNode = arr[level];
        // 找到合适的插入位置
        Node searchNode = parent;
        if (searchNode == null) {
            searchNode = skipNode.head;
        }
        while (searchNode.next.val <= num) {
            if (searchNode.val == num) {
                break;
            }
            searchNode = searchNode.next;
        }
        if (level == MAX_LEVEL - 1) {
            Node insertNode = searchNode;
            while (insertNode.next.val == num) {
                insertNode = insertNode.next;
            }
            Node node = new Node(num);
            insertNode(insertNode, node);
            return searchNode.val == num ? searchNode : node;
        }

        // 构造上下层级之间的关联关系
        Node insertedNode = add(searchNode.nextLevel, level + 1, num);
        if (searchNode.nextLevel == insertedNode) {
            return searchNode;
        }
        if (insertedNode == null || insertedNode.val == skipNode.head.getVal() || random.nextInt(2) == 0) {
            return null;
        }
        Node curNode = new Node(insertedNode.getVal());
        curNode.nextLevel = insertedNode;
        insertNode(searchNode, curNode);
        return curNode;
    }

    private void insertNode(Node curNode, Node insertedNode) {
        insertedNode.next = curNode.next;
        insertedNode.prev = curNode;
        curNode.next.prev = insertedNode;
        curNode.next = insertedNode;
    }

    public boolean erase(int num) {
        if (!search(num)) {
            return false;
        }
        erase(null, 0, num);
        return true;
    }

    private boolean erase(Node root, int level, int num) {
        if (level >= MAX_LEVEL) {
            return false;
        }
        Node searchNode = root;
        if (searchNode == null) {
            SkipNode skipNode = this.arr[level];
            searchNode = skipNode.head;
        }
        while (searchNode.next.val <= num) {
            if (searchNode.val == num) {
                break;
            }
            searchNode = searchNode.next;
        }
        if (level == MAX_LEVEL - 1) {
            Node deletedNode = searchNode;
            while (deletedNode.next.val == num) {
                deletedNode = deletedNode.next;
            }
            deleteNode(deletedNode);
            return deletedNode == root;
        }
        boolean erased = erase(searchNode.nextLevel, level + 1, num);
        if (searchNode.val != num || !erased) {
            return false;
        }
        searchNode.nextLevel = null;
        deleteNode(searchNode);
        return true;
    }

    private void deleteNode(Node node) {
        Node prev = node.prev;
        Node next = node.next;

        prev.next = next;
        next.prev = prev;

        node.prev = null;
        node.next = null;
        node.nextLevel = null;
    }

    public void printArr() {
        FileUtil.writeString("", new File("/tmp/out.txt"), StandardCharsets.UTF_8);
        printArr(0);
    }

    private void printArr(int level) {
        if (level >= MAX_LEVEL) {
            return;
        }
        FileUtil.appendString(arr[level].toString(), new File("/tmp/out.txt"), StandardCharsets.UTF_8);
        printArr(level + 1);
    }

    public void check() {
        for (int level = 0; level < MAX_LEVEL; level++) {
            arr[level].check();
        }
    }

    public static void main(String[] args) {
        Skiplist skiplist = new Skiplist();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 1; i <= 10000; i++) {
            skiplist.add(i);
            skiplist.erase(random.nextInt(1, 10010));
            skiplist.search(random.nextInt(1, 10010));
        }
    }
}
