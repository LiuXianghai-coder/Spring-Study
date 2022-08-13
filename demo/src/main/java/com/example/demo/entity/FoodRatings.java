package com.example.demo.entity;

import java.util.*;

/**
 * @author lxh
 * @date 2022/7/24-上午11:05
 */
public class FoodRatings {
    Map<String, TreeMap<Integer, Integer>> cuiMap = new HashMap<>();
    Map<Integer, TreeSet<String>> ratMap = new HashMap<>();
    Map<String, String> fcMap = new HashMap<>();

    Map<String, Integer> valMap = new HashMap<>();

    public FoodRatings(String[] foods, String[] cuisines, int[] ratings) {
        int n = foods.length;
        for (int i = 0; i < n; ++i) {
            valMap.put(foods[i], ratings[i]);
            fcMap.put(foods[i], cuisines[i]);

            TreeSet<String> ratTs = ratMap.getOrDefault(ratings[i], new TreeSet<>());
            ratTs.add(foods[i]);
            ratMap.put(ratings[i], ratTs);

            TreeMap<Integer, Integer> map = cuiMap.getOrDefault(cuisines[i], new TreeMap<>());
            map.put(ratings[i], map.getOrDefault(ratings[i], 0) + 1);
            cuiMap.put(cuisines[i], map);
        }
    }

    public void changeRating(String food, int newRating) {
        int rat = valMap.get(food);
        String cui = fcMap.get(food);
        TreeMap<Integer, Integer> tm = cuiMap.get(cui);
        tm.put(rat, tm.get(rat) - 1);
        Integer cnt = tm.get(rat);
        if (cnt <= 0) tm.remove(rat);

        TreeSet<String> foods = ratMap.get(rat);
        foods.remove(food);
        if (foods.isEmpty()) ratMap.remove(rat);
        TreeSet<String> ts = ratMap.getOrDefault(newRating, new TreeSet<>());
        ts.add(food);
        ratMap.put(newRating, ts);

        valMap.put(food, newRating);
        tm.put(newRating, tm.getOrDefault(newRating, 0) + 1);
    }

    public String highestRated(String cuisine) {
        TreeMap<Integer, Integer> tm = cuiMap.get(cuisine);
        Integer rat = tm.floorKey(Integer.MAX_VALUE);
        TreeSet<String> ts = ratMap.get(rat);
        if (ts == null) return null;
        return ts.first();
    }

    public int closestMeetingNode(int[] e, int n1, int n2) {
        int n = e.length;
        Map<Integer, List<Integer>> g1 = new HashMap<>();
        Map<Integer, List<Integer>> g2 = new HashMap<>();

        Map<Integer, Integer> map1 = new HashMap<>();
        map1.put(n1, 0);
        Map<Integer, Integer> map2 = new HashMap<>();
        map2.put(n2, 0);

        boolean[] visted = new boolean[n + 1];
        Arrays.fill(visted, false);
        buildGraph(g1, n1, e, 1, map1, visted);
        Arrays.fill(visted, false);
        buildGraph(g2, n2, e, 1, map2, visted);

        int ans = -1, min = 0x3FFFFFFF;
        for (Integer key : map1.keySet()) {
            int v1 = map1.get(key);
            if (!map2.containsKey(key)) continue;
            int v2 = map2.get(key);
            int m = Math.max(v1, v2);
            if (m < min) {
                ans = key;
                min = m;
            }
        }

        for (Integer key : map2.keySet()) {
            int v1 = map2.get(key);
            if (!map1.containsKey(key)) continue;
            int v2 = map1.get(key);
            int m = Math.max(v1, v2);
            if (m < min) {
                ans = key;
                min = m;
            }
        }

        return ans;
    }

    void buildGraph(Map<Integer, List<Integer>> g, int s, int[] e, int d,
                    Map<Integer, Integer> dist, boolean[] visted) {
        int end = e[s];
        if (end == -1) return;
        if (visted[end]) {
            int ld = dist.get(end);
            int cd = d - ld;
        }

        visted[end] = true;

        List<Integer> list = g.getOrDefault(s, new ArrayList<>());
        list.add(end);
        g.put(s, list);
        if (!dist.containsKey(end)) dist.put(end, d);
        buildGraph(g, end, e, d + 1, dist, visted);
    }

    FoodRatings() {}

    public static void main(String[] args) {
        FoodRatings obj = new FoodRatings();
        obj.closestMeetingNode(new int[]{1, 0, 0, 0, 0}, 2, 2);
    }
}
