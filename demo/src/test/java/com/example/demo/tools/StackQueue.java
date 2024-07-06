package com.example.demo.tools;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.Deque;
import java.util.LinkedList;

public class StackQueue<E> {

    private static Unsafe unsafe;

    static {
        try {
            // Fast path when we are trusted.
            unsafe = Unsafe.getUnsafe();
        } catch (SecurityException se) {
            // Slow path when we are not trusted.
            try {
                Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
                theUnsafe.setAccessible(true);
                unsafe = (Unsafe) theUnsafe.get(Unsafe.class);
            } catch (Exception e) {
                throw new RuntimeException("exception while trying to get Unsafe", e);
            }
        }
    }

    /*
        h1 ——> h
        h2 ——> H
        h3 ——> H'
        h4 ——> HR
     */
    private Deque<E> h1, h2, h3, h4;

    /*
        t1 ——> T
        t2 ——> T'
     */
    private Deque<E> t1, t2;

    private boolean needCopy = false;

    private int copyCnt = 0;

    public StackQueue() {
        this.h1 = new LinkedList<>();
        this.h2 = new LinkedList<>();
        this.h3 = new LinkedList<>();
        this.h4 = new LinkedList<>();

        this.t1 = new LinkedList<>();
        this.t2 = new LinkedList<>();
    }

    public static void main(String[] args) throws Throwable {
        StackQueue<Integer> queue = new StackQueue<>();
//
//        queue.offer(1);
//        queue.offer(2);
//        queue.offer(3);
//        queue.offer(4);
//        queue.offer(5);
//        Assert.isTrue(queue.poll() == 1, "出队元素应当为 1");
//        Assert.isTrue(queue.poll() == 2, "出队元素应当为 2");
//        Assert.isTrue(queue.poll() == 3, "出队元素应当为 3");
//        Assert.isTrue(queue.poll() == 4, "出队元素应当为 4");
//        Assert.isTrue(queue.poll() == 5, "出队元素应当为 5");
//
//        queue.offer(1);
//        Assert.isTrue(queue.poll() == 1, "出队元素应当为 1");
//        queue.offer(2);
//        Assert.isTrue(queue.poll() == 2, "出队元素应当为 2");
//        queue.offer(3);
//        Assert.isTrue(queue.poll() == 3, "出队元素应当为 3");
//        queue.offer(4);
//        Assert.isTrue(queue.poll() == 4, "出队元素应当为 4");
//        queue.offer(5);
//        Assert.isTrue(queue.poll() == 5, "出队元素应当为 5");

        LinkedList<Integer> l1 = new LinkedList<>();
        LinkedList<Integer> l2 = new LinkedList<>();

        l1.offer(1);
        l1.offer(2);
        l1.offer(3);
        l1.offer(4);

        l2.offer(5);
        l2.offer(6);

        System.out.println(l1);
        System.out.println(l2);

        long size = unsafe.objectFieldOffset(LinkedList.class.getDeclaredField("size"));
        long first = unsafe.objectFieldOffset(LinkedList.class.getDeclaredField("first"));
        long last = unsafe.objectFieldOffset(LinkedList.class.getDeclaredField("last"));

        System.out.println("============== after copy ==================");
//        unsafe.copyMemory(l1, size, l2, size, 4);
//        unsafe.copyMemory(l1, first, l2, first, 4);
        System.out.println(size);
        System.out.println(first);
        System.out.println(last);
        unsafe.compareAndSwapObject(l2, size, unsafe.getObject(l2, size), unsafe.getObject(l1, size));
        unsafe.compareAndSwapObject(l2, first, unsafe.getObject(l2, first), unsafe.getObject(l1, first));
        unsafe.compareAndSwapObject(l2, last, unsafe.getObject(l2, last), unsafe.getObject(l1, last));

        System.out.println(l1);
        System.out.println(l2);
    }

    public void offer(E item) {
        if (!needCopy) {
            if (lenDiff() > 0) {
                this.copyCnt = 0;
                this.t1.push(item);
            } else if (lenDiff() == 0) {
                this.t1.push(item);
                this.needCopy = true;
                shallowCopyOf(this.h2, this.h1);

                copyStep();
                copyStep();
            }
        } else {
            this.t2.push(item);
            copyStep();
            copyStep();
        }
    }

    public E poll() {
        if (!needCopy) {
            if (lenDiff() > 0) {
                return this.h2.pop();
            } else if (lenDiff() == 0) {
                E e = this.h2.pop();
                shallowCopyOf(this.h2, this.h1);
                needCopy = true;

                copyStep();
                copyStep();
                return e;
            }
        }
        E e = this.h1.pop();
        this.copyCnt -= 1;

        copyStep();
        copyStep();
        return e;
    }

    private void copyStep() {
        if (!needCopy) return;
        if (!this.h2.isEmpty() && !this.t1.isEmpty()) {
            copyCnt++;
            this.h3.push(this.t1.pop());
            this.h4.push(this.h2.pop());
            return;
        }

        if (this.h2.isEmpty() && !this.t1.isEmpty()) {
            needCopy = true;
            this.h3.push(this.t1.pop());
            return;
        }

        if (this.t1.isEmpty() && this.h2.isEmpty()) {
            if (copyCnt > 1) {
                needCopy = true;
                copyCnt--;
                this.h3.push(this.h4.pop());
            } else if (copyCnt == 1) {
                needCopy = false;
                copyCnt--;
                this.h3.push(this.h4.pop());
                this.h2 = this.h3;
                this.t1 = this.t2;

                this.h3 = new LinkedList<>();
                this.t2 = new LinkedList<>();
                this.h4 = new LinkedList<>();
                this.h1 = new LinkedList<>();
            } else if (copyCnt == 0) {
                needCopy = false;
                this.h2 = this.h3;
                this.t1 = this.t2;

                this.h3 = new LinkedList<>();
                this.t2 = new LinkedList<>();
                this.h4 = new LinkedList<>();
                this.h1 = new LinkedList<>();
            }
        }
    }

    private int lenDiff() {
        return this.h2.size() - this.t1.size();
    }

    private void shallowCopyOf(Deque<E> srcStack, Deque<E> tarStack) {
        unsafe.copyMemory(srcStack, 0, tarStack, 0, unsafe.getLong(srcStack, 0));
    }
}
