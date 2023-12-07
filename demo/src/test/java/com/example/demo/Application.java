package com.example.demo;


import java.util.*;

/**
 * @author xhliu2
 **/
public class Application {

    int[] cardPoints;

    public int maxScore(int[] cardPoints, int k) {
        this.cardPoints = cardPoints;
        return dfs(0, cardPoints.length - 1, k, 0);
    }

    int dfs(int left, int right, int k, int cur) {
        if (left < 0 || right >= cardPoints.length) return cur;
        if (k == 0) return cur;
        System.out.println("left=" + left + "\tright=" + right + "\tk=" + k);
        return Math.max(dfs(left, right - 1, k - 1, cur + cardPoints[right]),
                dfs(left + 1, right, k - 1, cur + cardPoints[left]));
    }

    public static void main(String[] args) {
        Application app = new Application();
        int[] arr = new int[]{96, 90, 41, 82, 39, 74, 64, 50};
        System.out.println(Arrays.stream(arr).reduce(Integer::sum));
    }
}
