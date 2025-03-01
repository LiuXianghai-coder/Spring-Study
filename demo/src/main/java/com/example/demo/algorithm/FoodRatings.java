package com.example.demo.algorithm;

import org.junit.jupiter.api.Assertions;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 *@author lxh
 */
public class FoodRatings {
    final TreeMap<String, TreeMap<Integer, TreeSet<String>>> cuisineMap = new TreeMap<>();
    final Map<String, Integer> food2Rating = new HashMap<>();
    final Map<String, String> food2Cuisine = new HashMap<>();

    public FoodRatings(String[] foods, String[] cuisines, int[] ratings) {
        for (int i = 0; i < foods.length; ++i) {
            String food = foods[i], cuisine = cuisines[i];
            int rating = ratings[i];
            cuisineMap.computeIfAbsent(cuisine, key -> new TreeMap<>());
            cuisineMap.get(cuisine).computeIfAbsent(rating, key -> new TreeSet<>());
            cuisineMap.get(cuisine).get(rating).add(food);
            food2Rating.put(food, rating);
            food2Cuisine.put(food, cuisine);
        }
    }

    public void changeRating(String food, int newRating) {
        Integer rawRating = food2Rating.get(food);
        if (rawRating == newRating) return;
        String cuisine = food2Cuisine.get(food);
        TreeMap<Integer, TreeSet<String>> treeMap = cuisineMap.get(cuisine);
        treeMap.get(rawRating).remove(food);
        if (treeMap.get(rawRating).isEmpty()) {
            treeMap.remove(rawRating);
        }
        treeMap.computeIfAbsent(newRating, key -> new TreeSet<>());
        treeMap.get(newRating).add(food);
        food2Rating.put(food, newRating);
    }

    public String highestRated(String cuisine) {
        if (!cuisineMap.containsKey(cuisine)) {
            return null;
        }
        TreeMap<Integer, TreeSet<String>> tm = cuisineMap.get(cuisine);
        if (tm == null) {
            return null;
        }
        TreeSet<String> ts = tm.get(tm.floorKey(Integer.MAX_VALUE));
        if (ts == null || ts.isEmpty()) {
            return null;
        }
        return ts.first();
    }

    public static void main(String[] args) {
        FoodRatings foodRatings = new FoodRatings(
                new String[]{"kimchi", "miso", "sushi", "moussaka", "ramen", "bulgogi"},
                new String[]{"korean", "japanese", "japanese", "greek", "japanese", "korean"},
                new int[]{9, 12, 8, 15, 14, 7}
        );
        Assertions.assertEquals("kimchi", foodRatings.highestRated("korean"));
        Assertions.assertEquals("ramen", foodRatings.highestRated("japanese"));
        foodRatings.changeRating("sushi", 16);
        Assertions.assertEquals("sushi", foodRatings.highestRated("japanese"));
        foodRatings.changeRating("ramen",16);
        Assertions.assertEquals("ramen", foodRatings.highestRated("japanese"));
    }
}
