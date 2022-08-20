package com.example.demo.algorithm;

/**
 * @author lxh
 */
public class SegmentTree {
    int[] tree = new int[100010];

    int[] lazy = new int[100010];

    /**
     * 用于区间求和
     *
     * @param rt 当前处理的子树的根节点
     */
    void pushUpSum(int rt) {
        tree[rt] = tree[rt << 1] + tree[rt << 1 | 1];
    }

    /**
     * 用于求区间最大值
     * @param rt 当前处理的子树的根节点
     */
    void pushUpMax(int rt) {
        tree[rt] = Math.max(tree[rt << 1], tree[rt << 1 | 1]);
    }

    /**
     * 用于区间求和，需要使用到懒标记
     * @param rt    处理的子树的根节点的位置索引
     * @param len   根节点统计的区间长度
     */
    void pushDownSum(int rt, int len) {
        tree[rt << 1] += lazy[rt] * (len - (len>>1));
        lazy[rt << 1] += lazy[rt];
        tree[rt << 1 | 1] += lazy[rt] * (len >> 1);
        lazy[rt << 1 | 1] += lazy[rt];
        lazy[rt] = 0;
    }

    /**
     * 求区间最大值，不需要乘以长度，因此不需要参数 len
     * @param rt    处理的子树的根节点的位置索引
     */
    void pushDownMax(int rt) {
        tree[rt << 1] += lazy[rt];
        lazy[rt << 1] += lazy[rt];
        tree[rt << 1 | 1] += lazy[rt];
        lazy[rt << 1 | 1] += lazy[rt];
        lazy[rt] = 0;
    }

    void build(int l, int r, int rt) {
        if (l >= r) {
            tree[rt] = 1; // 线段树的属性设置
            return;
        }

        int m = l + ((r - l) >> 1);
        build(rt << 1, l, m);
        build(rt << 1 | 1, m + 1, r);
        pushUpSum(rt);
    }

    /**
     * 单点更新，不需要用到懒标记
     */
    void update(int p, int val, int rt, int l, int r) {
        if (l >= r) {
            tree[rt] += val;
            return;
        }

        int m = l + ((r - l) >> 1);
        if (p <= m) update(p, val, rt << 1, l, m);
        else update(p, val, rt << 1 | 1, m + 1, r);
        pushUpSum(rt);
    }

    void update(int L, int R, int val, int rt, int l, int r) {
        if (L <= l && r <= R) {
            tree[rt] += val * (r - l + 1);
            lazy[rt] += val;
            return;
        }
        if (lazy[rt] > 0) pushDownSum(rt, r - l + 1);
        int m = l + ((r - l) >> 1);
        if (L <= m) update(L, R, val, rt << 1, l, m);
        if (R >= m) update(L, R, val, rt << 1 | 1, m + 1, r);
        pushUpSum(rt);
    }

    int query(int L, int R, int rt, int l, int r) {
        if (L <= l && r <= R) return tree[rt];
        if (lazy[rt] > 0) pushDownSum(rt, r - l + 1);
        int m = l + ((r - l) >> 1);
        int res = 0;
        if (L <= m) res += query(L, R, rt << 1, l, m);
        if (R > m) res += query(L, R, rt << 1 | 1, m + 1, r);
        return res;
    }
}
