package com.example.demo;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author xhliu
 * @create 2022-03-18-15:51
 **/
public class Heap<T extends Comparable<T>> {
    T[] arr;
    int N = 0;

    public Heap(T[] arr) {
        this.arr = arr;
    }

    private boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    public void insert(T t) {
        if (++N >= arr.length) ensureCapacity();

        arr[N] = t;
        swim(N);
    }

    public void ensureCapacity() {
        @SuppressWarnings("unchecked")
        T[] t = (T[]) new Comparable[N * 2];
        if (N >= 0) System.arraycopy(arr, 0, t, 0, N);
        this.arr = t;
    }

    public T delete() {
        if (isEmpty()) {
            throw new RuntimeException("队列为空");
        }

        T max = arr[1];
        exch(1, N--);
        sink(1);
        arr[N + 1] = null;

        return max;
    }

    static <T extends Comparable<T>> Heap<T> newInstance(T[] arr) {
        return new Heap<>(arr);
    }

//    public static <T extends Comparable<T>> void sort(T[] arr) {
//        Heap<T> heap = new Heap<>(arr.clone());
//        for (T t : arr) heap.insert(t);
//
//        for (int i = 0; i < arr.length; ++i) {
//            arr[i] = heap.delete();
//        }
//    }

    public static <T extends Comparable<T>> void sort(Comparable<T>[] pq) {
        int n = pq.length;

        // heapify phase
        for (int k = n/2; k >= 1; k--)
            sink(pq, k, n);

        // sortdown phase
        int k = n;
        while (k > 1) {
            exch(pq, 1, k--);
            sink(pq, 1, k);
        }
    }

    private static <T extends Comparable<T>> void sink(Comparable<T>[] pq, int k, int n) {
        while (2*k <= n) {
            int j = 2*k;
            if (j < n && less(pq, j, j + 1)) j++;
            if (!less(pq, k, j)) break;
            exch(pq, k, j);
            k = j;
        }
    }

    @SuppressWarnings("unchecked")
    private static <T extends Comparable<T>> boolean less(Comparable<T>[] pq, int i, int j) {
        return pq[i-1].compareTo((T) pq[j-1]) < 0;
    }

    private static void exch(Object[] pq, int i, int j) {
        Object swap = pq[i-1];
        pq[i-1] = pq[j-1];
        pq[j-1] = swap;
    }

    public void sort() {
        int n = N;
        for (int k = n/2; k >= 1; --k) sink(k);
        while (n > 1) {
            exch(1, n--);
            sink(1);
        }
    }

    private boolean less(int i , int j) {
        return arr[i].compareTo(arr[j]) < 0;
    }

    private void exch(int i, int j) {
        T t = arr[i]; arr[i] = arr[j]; arr[j] = t;
    }

    private void swim(int k) {
        while (k > 1 && less(k / 2, k)) {
            exch(k / 2, k);
            k /= 2;
        }
    }

    private void sink(int k) {
        while (k * 2 <= N) {
            int j  = 2 * k;
            if (j < N && less(j, j + 1)) j++;
            if (!less(k, j)) break;
            exch(k, j);

            k = j;
        }
    }

    public static void main(String[] args) {
        Integer[] arr = new Integer[20];
        Random random = ThreadLocalRandom.current();
        for (int i = 0; i < arr.length; ++i) {
            arr[i] = random.nextInt() % 100;
        }

        Heap<Integer> heap = new Heap<>(arr);
        heap.sort();

        System.out.println("before sort: " + Arrays.toString(arr));
        sort(arr);
        System.out.println("after sort: " + Arrays.toString(arr));
    }
}
