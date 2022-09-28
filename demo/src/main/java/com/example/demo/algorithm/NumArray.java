package com.example.demo.algorithm;

import com.example.demo.common.BackupInfo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author lxh
 */
public class NumArray {
    Node[] tr;
    static class Node {
        int l, r, v;
        Node(int _l, int _r) {
            l = _l; r = _r;
        }
    }
    void build(int u, int l, int r) {
        tr[u] = new Node(l, r);
        if (l == r) return;
        int mid = l + r >> 1;
        build(u << 1, l, mid);
        build(u << 1 | 1, mid + 1, r);
    }
    void update(int u, int x, int v) {
        if (tr[u].l == x && tr[u].r == x) {
            tr[u].v += v;
            return ;
        }
        int mid = tr[u].l + tr[u].r >> 1;
        if (x <= mid) update(u << 1, x, v);
        else update(u << 1 | 1, x, v);
        pushup(u);
    }
    int query(int u, int l, int r) {
        if (l <= tr[u].l && tr[u].r <= r) return tr[u].v;
        int mid = tr[u].l + tr[u].r >> 1;
        int ans = 0;
        if (l <= mid) ans += query(u << 1, l, r);
        if (r > mid) ans += query(u << 1 | 1, l, r);
        return ans;
    }
    void pushup(int u) {
        tr[u].v = tr[u << 1].v + tr[u << 1 | 1].v;
    }

    int[] nums;
    public NumArray(int[] _nums) {
        nums = _nums;
        int n = nums.length;
        tr = new Node[n * 4];
        build(1, 1, n);
        for (int i = 0; i < n; i++) update(1, i + 1, nums[i]);
    }
    public void update(int index, int val) {
        update(1, index + 1, val - nums[index]);
        nums[index] = val;
    }
    public int sumRange(int left, int right) {
        return query(1, left + 1, right + 1);
    }

    public static final Object lock = new Object();

    public List<?> update() {
        return null;
    }

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        NumArray array = new NumArray(new int[]{1, 2, 3, 4});
        Method method = NumArray.class.getDeclaredMethod("update");
        Object res = method.invoke(array);
        System.out.println(res == null);
        System.out.println(null instanceof BackupInfo);
    }
}
