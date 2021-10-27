package com.example.demo;

import com.example.demo.inter.UnionFind;

/**
 * @author xhliu2
 * @create 2021-10-12 9:47
 **/
public class UnionFindImpl implements UnionFind {
    private final int[] parent;
    private final int[] size;
    private int count;

    public UnionFindImpl(final int n) {
        parent = new int[n];
        size = new int[n];
        count = n;
        for (int i = 0; i < n; ++i) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    @Override
    public void union(int p, int q) {
        int i = find(p);
        int j = find(q);

        if (i == j) return;

        if (size[i] < size[j]) {
            parent[i] = j;
            size[j] += size[i];
        } else {
            parent[j] = i;
            size[i] += size[j];
        }

        count--;
    }

    @Override
    public boolean connect(int p, int q) {
        return find(p) == find(q);
    }

    @Override
    public int count() {
        return count;
    }

    private int find (int p) {
        while (p != parent[p]) {
            parent[p] = parent[parent[p]];
            p = parent[p];
        }
        return p;
    }
}
