package com.example.demo.algorithm;

/**
 * @author lxh
 */
public class SegmentTree {
    static class Node {
        int l, r;
        int lazy, val;
        Node(int _l, int _r) {
            l = _l; r =_r;
        }

        Node(){}
    }

    Node[] tree;

    public SegmentTree(int n) {
        tree = new Node[n + 1];
    }

    void build(int rt, int l, int r) {
        tree[rt] = new Node(l, r);
        if (l >= r) return;
        int mid = l + ((r - l) >> 1);
        build(rt << 1, l, mid);
        build(rt << 1 | 1, mid + 1, r);
    }

    void update(int rt, int L, int R, int l, int r, int val) {
        if (L <= l && r <= R) {
            tree[rt].val += (r - l + 1) * val;
            tree[rt].lazy += val;
            return;
        }

        if (tree[rt].lazy > 0) pushDown(rt, r - l + 1);
        int mid = l + ((r - l) >> 1);
        if (L <= mid) update(rt << 1, L, R, l, mid, val);
        else update(rt << 1 | 1, L, R, mid + 1, r, val);
        pushUp(rt);
    }

    void pushDown(int rt, int len) {
        int l = rt << 1, r = rt << 1 | 1;
        tree[l].val += tree[rt].lazy * (len - (len >> 1));
        tree[l].lazy += tree[rt].lazy;
        tree[r].val += tree[rt].lazy * (len >> 1);
        tree[r].lazy += tree[rt].lazy;
        tree[rt].lazy = 0;
    }

    void pushUp(int rt) {
        tree[rt].val = tree[tree[rt].l].val + tree[tree[rt].r].val;
    }
}