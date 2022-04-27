package com.example.demo.inter;

import com.example.demo.domain.Data;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author xhliu
 * @create 2022-03-18-15:00
 **/
@SpringBootTest
public class Solution {
    public String addStrings(String _num1, String _num2) {
        int n1 = _num1.length(), n2 = _num2.length();
        char[] num1, num2;

        if (n1 >= n2) {
            num1 = _num1.toCharArray();
            num2 = _num2.toCharArray();
        } else {
            num2 = _num1.toCharArray();
            num1 = _num2.toCharArray();
        }

        int n = Math.max(n1, n2);
        char[] ans = new char[n + 1];

        int bit = 0, i, j, k;
        for (i = num1.length - 1, j = num2.length - 1, k = n;
             i >= 0 && j >= 0; --i, --j, --k) {
            int add = num1[i] - '0' + num2[j] - '0' + bit;
            ans[k] = (char) ('0' + add % 10);
            bit = add / 10;
        }

        while (i >= 0) {
            int add = num1[i--] - '0' + bit;
            ans[k--] = (char) ('0' + add % 10);
            bit = add / 10;
        }

        if (bit > 0) ans[k--] = (char) (bit + '0');

        return String.valueOf(ans, k + 1, ans.length - k - 1);
    }

    int fibonacciNum(int n) {
        int[] dp = new int[n + 1];
        dp[1] = 1;
        dp[2] = 1;
        for (int i = 3; i <= n; ++i)
            dp[i] = dp[i - 1] + dp[i - 2];
        return dp[n];
    }

    @Test
    public void test() throws IOException {
        URL url = Solution.class.getClassLoader().getResource("nums");
        assert url != null;
        BufferedReader reader = new BufferedReader(new FileReader(url.getFile()));
        String line;
        List<Integer> list = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            list.add(Integer.parseInt(line));
        }

        list.sort(Integer::compareTo);

        System.out.println("list size=" + list.size());

        Integer[] arr = new Integer[list.size()];
        Integer[] nums = new Integer[list.size()];
        list.toArray(nums);

        List<int[]> list1 = new ArrayList<>();
        for (int i = 0; i < nums.length; ++i) {
            list1.add(new int[]{nums[i], i});
        }
        list1.sort(Comparator.comparingInt(a -> a[0]));

        int target = -760627172;

        System.out.println(pick(target, list1));
    }

    int pick(int target, List<int[]> list) {
        int n = list.size();
        int start, end;

        int lo = 0, hi = n - 1;
        while (lo <= hi) {
            int mid = lo + ((hi - lo) >> 1);
            if (list.get(mid)[0] >= target) hi = mid - 1;
            else lo = mid + 1;
        }
        start = lo;
        System.out.println("lo=" + lo + "\thi=" + hi + "\tval=" + Arrays.toString(list.get(lo)));

        lo = 0; hi = n - 1;
        while (lo <= hi) {
            int mid = lo + ((hi - lo) >> 1);
            if (list.get(mid)[0] <= target) lo = mid + 1;
            else hi = mid - 1;
        }
        end = hi;
        System.out.println("lo=" + lo + "\thi=" + hi + "\tval=" + Arrays.toString(list.get(hi)));

        int len = end - start + 1;
        int rd = new Random().nextInt(len);

        // System.out.println("start=" + start + "\tend=" + end + "\trd=" + rd);

        return list.get(start + rd)[1];
    }

    final static class Node<T> {
        T oldVal, newVal;

        public Node(T oldVal, T newVal) {
            this.oldVal = oldVal;
            this.newVal = newVal;
        }

        public T getOldVal() {
            return oldVal;
        }

        public void setOldVal(T oldVal) {
            this.oldVal = oldVal;
        }

        public T getNewVal() {
            return newVal;
        }

        public void setNewVal(T newVal) {
            this.newVal = newVal;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "oldVal=" + oldVal +
                    ", newVal=" + newVal +
                    '}';
        }
    }

    /**
     * 比较两个对象之间的每个属性是否完全一致，如果不一致，
     * 则使用一个 Map 记录不同的属性位置以及原有旧值和新值
     *
     * @param o1 : 旧有数据对象
     * @param o2 : 新的数据对象
     * @return : 如果两个对象的属性完全一致，返回 true，否则，返回 false
     */
    boolean compObject(Object o1, Object o2) {
        Map<String, Node<Object>> map = new HashMap<>();
        boolean res =  dfs(o1, o2, "", map);

        for (String key : map.keySet())
            log.info(key + "——>" + map.get(key));

        return res;
    }

    boolean dfs(Object o1, Object o2, String prefix, Map<String, Node<Object>> map) {
        if (o1 == null && o2 == null) return true;
        if (o1 == null) {
            map.put(prefix, new Node<>(null, o2));
            return false;
        }

        if (o2 == null) {
            map.put(prefix, new Node<>(o1, null));
            return false;
        }

        if (o1.getClass() != o2.getClass()) {
            throw new RuntimeException("o1 和 o2 的对象类型不一致");
        }

        boolean res = true;

        // 检查当前对象的属性以及属性对象的子属性的值是否一致
        Field[] fields = o1.getClass().getDeclaredFields();
        for (Field field : fields) {
            int modifiers = field.getModifiers();
            if ((modifiers & Modifier.STATIC) != 0) continue; // 过滤静态修饰符修饰的字段
            field.setAccessible(true);
            String curFiled = prefix + (prefix.length() > 0 ? "." : "") + field.getName();
            try {
                Object v1 = field.get(o1), v2 = field.get(o2);
                if (isBasicType(field.getType())) {
                    if (v1 == null && v2 == null) continue;
                    if (v1 == null) {
                        map.put(curFiled, new Node<>(null, v2));
                        continue;
                    }

                    if (v2 == null) {
                        map.put(curFiled, new Node<>(v1, null));
                        continue;
                    }

                    if (equalsObj(v1, v2)) continue;
                    map.put(curFiled, new Node<>(v1, v2));
                    res = false;
                    continue;
                }

                res |= dfs(v1, v2, curFiled, map);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return res;
    }

    static final Set<Class<?>> BASIC_CLASS_SET = new HashSet<>();

    static {
        BASIC_CLASS_SET.add(Number.class);
        BASIC_CLASS_SET.add(Byte.class);
        BASIC_CLASS_SET.add(Short.class);
        BASIC_CLASS_SET.add(Integer.class);
        BASIC_CLASS_SET.add(Long.class);
        BASIC_CLASS_SET.add(Float.class);
        BASIC_CLASS_SET.add(Double.class);
        BASIC_CLASS_SET.add(String.class);
        BASIC_CLASS_SET.add(Character.class);
        BASIC_CLASS_SET.add(Date.class);
        BASIC_CLASS_SET.add(java.sql.Date.class);
        BASIC_CLASS_SET.add(LocalDateTime.class);
        BASIC_CLASS_SET.add(LocalDate.class);
        BASIC_CLASS_SET.add(byte.class);
        BASIC_CLASS_SET.add(short.class);
        BASIC_CLASS_SET.add(int.class);
        BASIC_CLASS_SET.add(float.class);
        BASIC_CLASS_SET.add(long.class);
        BASIC_CLASS_SET.add(double.class);
    }

    boolean isBasicType(Class<?> c) {
        return BASIC_CLASS_SET.contains(c);
    }

    /**
     * 比较两个对象是否相等，如果对象实现了 Comparable 接口，则使用 compareTo 方法进行比较
     * 否则使用 Object 的 equals 方法进行对象的比较
     */
    @SuppressWarnings("unchecked")
    boolean equalsObj(Object o1, Object o2) {
        if (o1 instanceof Comparable)
            return ((Comparable<Object>) o1).compareTo(o2) == 0;
        return o1.equals(o2);
    }

    @Resource
    private ObjectMapper mapper;

    private final static Logger log = LoggerFactory.getLogger(Solution.class);

    @Test
    public void compareTest() {
        Solution solution = new Solution();

        solution.compObject(Data.EXAMPLE_ONE, Data.EXAMPLE_TWO);
    }
}
