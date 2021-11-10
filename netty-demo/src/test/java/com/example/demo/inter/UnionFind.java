package com.example.demo.inter;

public interface UnionFind {
    void union(int p, int q);

    boolean connect(int p, int q);

    int count();
}
