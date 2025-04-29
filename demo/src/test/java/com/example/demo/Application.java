package com.example.demo;

import org.junit.jupiter.api.Assertions;

import java.util.*;

/**
 * @author xhliu2
 **/
public class Application {

    public long countSubarrays(int[] nums, int minK, int maxK) {
        long ans = 0L;
        int n = nums.length;
        Deque<Integer> maxDeque = new ArrayDeque<>();
        Deque<Integer> minDeque = new ArrayDeque<>();
        for (int i = 0, last = -1, lst = -1; i < n; ++i) {
            while (!maxDeque.isEmpty() && nums[maxDeque.peekLast()] <= nums[i]) {
                maxDeque.pollLast();
            }
            maxDeque.offer(i);
            while (!minDeque.isEmpty() && nums[minDeque.peekLast()] >= nums[i]) {
                minDeque.pollLast();
            }
            minDeque.offer(i);

            while (!maxDeque.isEmpty() && nums[maxDeque.peek()] > maxK) {
                last = maxDeque.pollFirst();
            }
            while (!minDeque.isEmpty() && nums[minDeque.peek()] < minK) {
                lst = minDeque.pollFirst();
            }

            int l1 = 0, l2= 0, l3 = 0, l4 = 0;
            if (!maxDeque.isEmpty() && nums[maxDeque.peek()] == maxK) {
                l1 = last;
                l2 = maxDeque.peek();
            }
            if (!minDeque.isEmpty() && nums[minDeque.peek()] == minK) {
                l3 = lst;
                l4 = minDeque.peek();
            }
            ans += Math.max(0, Math.min(l2, l4) - Math.max(l1, l3));
        }
        return ans;
    }

    public static void main(String[] args) throws Throwable {
        Application app = new Application();
        Assertions.assertEquals(2, app.countSubarrays(new int[]{1, 3, 5, 2, 7, 5}, 1, 5));
        Assertions.assertEquals(10, app.countSubarrays(new int[]{1, 1, 1, 1}, 1, 1));
    }
}
