package com.example.demo;

import com.google.common.collect.Lists;

import java.util.*;

/**
 *@author lxh
 */
public class TaskManager {

    // taskId -> userId
    private final Map<Integer, Integer> map2 = new HashMap<>();
    // userId -> taskId -> priority
    private final Map<Integer, TreeMap<Integer, Integer>> map3 = new HashMap<>();
    // priority -> taskId -> userId
    private final TreeMap<Integer, TreeMap<Integer, Integer>> map4 = new TreeMap<>();

    public TaskManager(List<List<Integer>> tasks) {
        for (List<Integer> item : tasks) {
            Integer userId = item.get(0), taskId = item.get(1), priority = item.get(2);

            map2.put(taskId, userId);

            map3.computeIfAbsent(userId, any -> new TreeMap<>());
            map3.get(userId).put(taskId, priority);

            map4.computeIfAbsent(priority, any -> new TreeMap<>());
            map4.get(priority).put(taskId, userId);
        }
    }

    public void add(int userId, int taskId, int priority) {

        map2.put(taskId, userId);

        map3.computeIfAbsent(userId, any -> new TreeMap<>());
        map3.get(userId).put(taskId, priority);

        map4.computeIfAbsent(priority, any -> new TreeMap<>());
        map4.get(priority).put(taskId, userId);
    }

    public void edit(int taskId, int newPriority) {
        Integer userId = map2.get(taskId);

        Integer rawPriority = map3.get(userId).get(taskId);

        map3.get(userId).put(taskId, newPriority);

        map4.get(rawPriority).remove(taskId);
        if (map4.get(rawPriority).isEmpty()) {
            map4.remove(rawPriority);
        }
        map4.computeIfAbsent(newPriority, any -> new TreeMap<>());
        map4.get(newPriority).put(taskId, userId);
    }

    public void rmv(int taskId) {
        Integer userId = map2.get(taskId);

        Integer rawPriority = map3.get(userId).get(taskId);
        map3.get(userId).remove(taskId);

        map2.remove(taskId);

        map4.get(rawPriority).remove(taskId);
        if (map4.get(rawPriority).isEmpty()) {
            map4.remove(rawPriority);
        }
    }

    public int execTop() {
        Integer floorKey = map4.floorKey(Integer.MAX_VALUE);
        if (floorKey == null) return -1;
        Map.Entry<Integer, Integer> taskEntry = map4.get(floorKey).floorEntry(Integer.MAX_VALUE);
        if (taskEntry == null) {
            return -1;
        }

        map4.get(floorKey).remove(taskEntry.getKey());
        if (map4.get(floorKey).isEmpty()) {
            map4.remove(floorKey);
        }
        return taskEntry.getValue();
    }

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager(List.of(List.of(2, 26, 15), List.of(6, 1, 19)));
        taskManager.edit(26, 15);
        System.out.println(taskManager.execTop());
    }
}
