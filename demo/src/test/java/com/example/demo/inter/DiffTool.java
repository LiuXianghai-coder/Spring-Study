package com.example.demo.inter;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Objects;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * @author xhliu
 * @create 2022-03-18-15:00
 **/
@SpringBootTest
public class DiffTool {
    static final Set<Class<?>> BASIC_CLASS_SET = new HashSet<>();

    static {
        BASIC_CLASS_SET.add(Number.class);
        BASIC_CLASS_SET.add(Byte.class);
        BASIC_CLASS_SET.add(Boolean.class);
        BASIC_CLASS_SET.add(Short.class);
        BASIC_CLASS_SET.add(Integer.class);
        BASIC_CLASS_SET.add(Long.class);
        BASIC_CLASS_SET.add(Float.class);
        BASIC_CLASS_SET.add(Double.class);
        BASIC_CLASS_SET.add(BigDecimal.class);
        BASIC_CLASS_SET.add(BigInteger.class);

        BASIC_CLASS_SET.add(String.class);
        BASIC_CLASS_SET.add(Character.class);
        BASIC_CLASS_SET.add(Date.class);
        BASIC_CLASS_SET.add(java.sql.Date.class);
        BASIC_CLASS_SET.add(LocalDateTime.class);
        BASIC_CLASS_SET.add(LocalDate.class);
        BASIC_CLASS_SET.add(Instant.class);
    }

    final static class Node<T> {
        final T oldVal, newVal;

        public Node(T oldVal, T newVal) {
            this.oldVal = oldVal;
            this.newVal = newVal;
        }

        public T getOldVal() {
            return oldVal;
        }

        public T getNewVal() {
            return newVal;
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
    static boolean compObject(Object o1, Object o2) {
        Map<String, Node<Object>> map = compare(o1, o2);
        return map.size() == 0;
    }

    /**
     * 比较两个对象，通过反射的方式递归地对每个字段进行比较，对于不同的字段，将会记录当前的递归字段属性，
     * 同时通过一个 {@code Node} 对象来记录两者之间该属性的旧值和新值
     *
     * @param oldObj : 旧值对象
     * @param newObj : 新值对象
     * @return 记录两个对象不同属性的 Map，Map 中存有的 {@code key} 应当是以 {@code .} 的分隔字符串形式
     */
    static Map<String, Node<Object>> compare(Object oldObj, Object newObj) {
        Map<String, Node<Object>> map = new HashMap<>();
        dfs(oldObj, newObj, "", map);
        return map;
    }

    static boolean dfs(Object o1, Object o2, String prefix, Map<String, Node<Object>> map) {
        if (o1 == null && o2 == null) return true;
        if (o1 == null) {
            map.put(prefix, new Node<>(null, o2));
            return false;
        }

        if (o2 == null) {
            map.put(prefix, new Node<>(o1, null));
            return false;
        }

        Class<?> c1 = o1.getClass(), c2 = o2.getClass();

        checkParams(c1 != c2, "o1 和 o2 的对象类型不一致");

        if (isBasicType(c1)) {
            boolean tmp = o1.equals(o2);
            if (!tmp) map.put(prefix, new Node<>(o1, o2));
            return false;
        }

        if (isEnum(c1)) {
            if (o1 != o2) map.put(prefix, new Node<>(o1, o2));
            return o1 == o2;
        }

        if (isMap(c1)) return equalsMap((Map<?, ?>) o1, (Map<?, ?>) o2, prefix, map);
        if (isCollection(c1)) return equalsCollection(o1, o2, prefix, map);

        boolean res = true;
        // 检查当前对象的属性以及属性对象的子属性的值是否一致
        Field[] fields = o1.getClass().getDeclaredFields();
        for (Field field : fields) {
            int modifiers = field.getModifiers();
            if ((modifiers & Modifier.STATIC) != 0) continue; // 过滤静态修饰符修饰的字段
            field.setAccessible(true);

            String curFiled = prefix + (prefix.length() > 0 ? "." : "") + field.getName();
            try {
                final Class<?> fieldClass = field.getType();
                Object v1 = field.get(o1), v2 = field.get(o2);
                if (checkHandle(checkBasicType(v1, v2, fieldClass, curFiled, map))) continue;
                if (checkHandle(checkCollection(v1, v2, fieldClass, curFiled, map))) continue;
                if (checkHandle(checkMap(v1, v2, fieldClass, curFiled, map))) continue;
                if (checkHandle(checkEnum(v1, v2, fieldClass))) continue;

                res &= dfs(v1, v2, curFiled, map);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return res;
    }

    final static int EQUALS = 1 << 1;     // 表示比较的对象的当前属性相等
    final static int NO_EQUALS = 1 << 2;     // 表示当前对象的类型能够进行处理，但是两个对象值并不相等
    final static int DISABLE = 1 << 3;     // 表示当前传入的对象该方法无法进行处理

    final static int PRIME = 51; // 一个比较正常的质数，这个质数将会作为进位数来计算对象的 hash 值

    private static boolean checkHandle(int handle) {
        return handle == EQUALS || handle == NO_EQUALS;
    }

    private static int checkBasicType(
            Object v1, Object v2, Class<?> fieldClass,
            String curFiled, Map<String, Node<Object>> map
    ) {

        if (isBasicType(fieldClass)) {
            if (v1 == null && v2 == null) return EQUALS;
            if (v1 == null) {
                map.put(curFiled, new Node<>(null, v2));
                return NO_EQUALS;
            }

            if (v2 == null) {
                map.put(curFiled, new Node<>(v1, null));
                return NO_EQUALS;
            }

            if (equalsObj(v1, v2)) return 1 << 1;
            map.put(curFiled, new Node<>(v1, v2));

            return NO_EQUALS;
        }

        return DISABLE;
    }

    private static int checkCollection(
            Object v1, Object v2, Class<?> fieldClass,
            String curFiled, Map<String, Node<Object>> map
    ) {
        if (isCollection(fieldClass))
            return equalsCollection(v1, v2, curFiled, map) ? EQUALS : NO_EQUALS;
        return DISABLE;
    }

    private static int checkMap(
            Object v1, Object v2, Class<?> fieldClass,
            String curFiled, Map<String, Node<Object>> map
    ) {
        if (isMap(fieldClass))
            return equalsMap((Map<?, ?>) v1, (Map<?, ?>) v2, curFiled, map) ? EQUALS : NO_EQUALS;

        return DISABLE;
    }

    private static int checkEnum(
            Object v1, Object v2, Class<?> fieldClass
    ) {
        if (isEnum(fieldClass)) {
            return equalsEnum((Enum<?>) v1, (Enum<?>) v2) ? EQUALS : NO_EQUALS;
        }

        return DISABLE;
    }

    static boolean isBasicType(Class<?> c) {
        if (c.isPrimitive()) return true;
        return BASIC_CLASS_SET.contains(c);
    }

    static boolean isCollection(Class<?> c) {
        return Collection.class.isAssignableFrom(c);
    }

    static boolean isMap(Class<?> c) {
        return Map.class.isAssignableFrom(c);
    }

    static boolean isEnum(Class<?> c) {
        return c == Enum.class;
    }

    // 检查两个枚举类型数据是否相同
    static boolean equalsEnum(Enum<?> o1, Enum<?> o2) {
        checkParams(o1.getClass() != o2.getClass(), "o1 和 o2 不同时为枚举类型");
        return o1 == o2;
    }

    /**
     * 比较两个对象是否相等，如果对象实现了 Comparable 接口，则使用 compareTo 方法进行比较
     * 否则使用 Object 的 equals 方法进行对象的比较
     *
     * @param o1 : 旧值数据对象
     * @param o2 : 新值数据对象
     */
    @SuppressWarnings("unchecked")
    static boolean equalsObj(Object o1, Object o2) {
        if (o1 instanceof Comparable)
            return ((Comparable<Object>) o1).compareTo(o2) == 0;
        return o1.equals(o2);
    }

    /**
     * 判断两个集合（Collection）中的元素是否相同，这里的实现只针对 Set 和 List <br />
     * 对于 Set : 如果存在不同的元素，则直接将两个集合作为比较对象保存到 differMap 中 <br />
     * 对于 List : 如果相同的索引位置的元素不同，那么会记录当前元素的索引位置的新旧值到 differMap，
     * 如果两个列表的长度不一致，则会使用 null 来代替不存在的元素，对于元素的比较同样基于 {@code dfs}<br />
     * 对于数组 : 首先将数组转换为对应的 {@code List}，再使用 List 的比较方法进行比较 <br />
     * <p>
     * <br />
     * <br />
     * 对于其它的集合类型，将会抛出一个 {@code RuntimeException}
     * <br />
     * <br />
     *
     * @param o1        : 旧集合数据对象
     * @param o2        : 新集合数据对象
     * @param prefix    : 当前集合属性所在的级别的前缀字符串表现形式
     * @param differMap : 存储不同属性字段的 Map
     */
    static boolean equalsCollection(
            Object o1, Object o2,
            String prefix, Map<String, Node<Object>> differMap
    ) {
        Class<?> c1 = o1.getClass(), c2 = o2.getClass();
        checkParams(c1 != c2, "集合 o1 和 o2 的类型不一致.");

        /*
            对于集合来讲，只能大致判断一下两个集合的元素是否是一致的，
            这是由于集合本身不具备随机访问的特性，因此如果两个集合存在不相等的元素，
            那么将会直接将两个集合存储的不同节点中
         */
        if (o1 instanceof Set) {
            // 分别计算两个集合的信息指纹
            long h1 = 0, h2 = 0;
            long hash = BigInteger
                    .probablePrime(32, current())
                    .longValue(); // 随机的大质数用于随机化信息指纹

            Set<?> s1 = (Set<?>) o1, s2 = (Set<?>) o2;

            for (Object obj : s1) h1 += genHash(obj) * hash;
            for (Object obj : s2) h2 += genHash(obj) * hash;

            if (h1 != h2) {
                differMap.put(prefix, new Node<>(s1, s2));
                return false;
            }

            return true;
        }

        /*
            对于列表来讲，由于列表的元素存在顺序，
            因此可以针对不同的索引位置的元素进行对应的比较
         */
        if (o1 instanceof List) {
            List<?> list1 = (List<?>) o1, list2 = (List<?>) o2;
            return differList(list1, list2, prefix, differMap);
        }

        /*
            对于数组类型的处理，可以转换为 List 进行类似的处理
         */
        if (c1.isArray()) {
            List<?> list1 = Arrays.stream((Object[]) o1).collect(Collectors.toList());
            List<?> list2 = Arrays.stream((Object[]) o2).collect(Collectors.toList());
            return differList(list1, list2, prefix, differMap);
        }

        log.debug("type={}", o1.getClass());
        throw new RuntimeException("未能处理的集合类型异常");
    }

    /**
     * 比较两个 {@code List} 对象之间的不同元素
     */
    static boolean differList(
            List<?> list1, List<?> list2,
            String prefix, Map<String, Node<Object>> differMap
    ) {
        boolean res = true;
        Map<String, Node<Object>> tmpMap = new HashMap<>(); // 记录相同索引位置的索引元素的不同

        int i;
        for (i = 0; i < list1.size() && i < list2.size(); ++i) {
            res &= dfs(list1.get(i), list2.get(i), getListPrefix(prefix, i), tmpMap);
        }

        differMap.putAll(tmpMap); // 添加到原有的不同 differMap 中

        // 后续如果集合存在多余的元素，那么肯定这两个位置的索引元素不同
        while (i < list1.size()) {
            res = false;
            differMap.put(getListPrefix(prefix, i), new Node<>(list1.get(i), null));
            i++;
        }
        while (i < list2.size()) {
            res = false;
            differMap.put(getListPrefix(prefix, i), new Node<>(null, list2.get(i)));
            i++;
        }
        // 后续元素处理结束

        return res;
    }

    /**
     * 比较两个 Map 属性值，对于不相交的 key，使用 null 来代替现有的 key 值
     * 当两个 Map 中都存在相同的 key 是，将会使用递归处理 {@code dfs} 继续比较 value 是否一致
     *
     * @param m1        : 旧值属性对象 Map 字段
     * @param m2        : 新值属性对象的 Map 字段
     * @param prefix    : 此时已经处理的对象的字段深度
     * @param differMap : 记录不同的属性值的 Map
     */
    static boolean equalsMap(
            Map<?, ?> m1, Map<?, ?> m2,
            String prefix, Map<String, Node<Object>> differMap
    ) {
        checkParams(m1.getClass() != m2.getClass(), "map1 和 map2 类型不一致");

        boolean res = true;

        // 首先比较两个 Map 都存在的 key 对应的 value 对象
        for (Object key : m1.keySet()) {
            String curPrefix = getFieldPrefix(prefix, key.toString());
            if (!m2.containsKey(key)) { // 如果 m2 不包含 m1 的 key，此时是一个不同元素值
                differMap.put(curPrefix, new Node<>(m1.get(key), null));
                res = false;
                continue;
            }

            res &= dfs(m1.get(key), m2.get(key), curPrefix, differMap);
        }
        // 检查 m1 中存在没有 m2 的 key 的情况
        for (Object key : m2.keySet()) {
            String curPrefix = getFieldPrefix(prefix, key.toString());
            if (!m1.containsKey(key)) {
                differMap.put(curPrefix, new Node<>(null, m2.get(key)));
                res = false;
            }
        }

        return res;
    }

    private static void checkParams(boolean b, String s) {
        if (b) {
            throw new RuntimeException(s);
        }
    }

    private static String getFieldPrefix(String prefix, String key) {
        return prefix + (prefix.length() > 0 ? "." : "") + key;
    }

    private static String getListPrefix(String prefix, int key) {
        return prefix + (prefix.length() > 0 ? "#" : "") + key;
    }

    /**
     * 获取传入的参数对象的 hash 值，具体的计算方式为: 遍历当前对象的所有属性字段，将每个字段视为一个
     * {@code PRIME} 进制数的一个数字，最终得到这个数将被视为当前对象的 hash 值
     * <br />
     * <br />
     * 对于基本数据类型来讲，将会将其强制转换为 {@code long} 类型的整数参与计算
     * <br />
     * 而对于集合类型 {@code Collection} 和数组类型来讲，将会将整个集合整体视为一个字段，
     * 将集合中所有元素按照相同的方式计算 hash 值，最后相加即为该集合的 hash 值
     * <br />
     * 对于其它已经自定义 hashCode 方法的对象（如 {@code BigInteger}、{@code Date} 等），
     * 将使用 {@code com.google.common.base.Objects} 的 hashCode 计算对应的 hash 值
     * <br />
     * 对于其它类型的属性，由于不存在重写的 hashcode 方法，这些属性字段将被视为独立对象递归进行处理
     * <br />
     *
     * @param obj : 待计算 hashcode 的对象
     * @return : 该对象生成的 hash 值
     */
    static long genHash(Object obj) {
        Class<?> c = obj.getClass();
        long ans = 0L;

        // 能够自行产生 hashcode 的类型
        if (c.isPrimitive() || isBasicType(c) || isEnum(c) || isMap(c)) {
            return Objects.hashCode(obj);
        }

        if (c.isArray()) { // 针对数组类型
            Iterator<?> iterator = Arrays.stream(((Object[]) obj)).iterator();
            while (iterator.hasNext())
                ans = ans * PRIME + genHash(iterator.next());
            return ans;
        }

        if (Collection.class.isAssignableFrom(c)) { // 针对集合类型
            for (Object tmp : (Collection<?>) obj)
                ans = ans * PRIME + genHash(tmp);
            return ans;
        }

        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            int modifier = field.getModifiers();
            if ((modifier & Modifier.STATIC) != 0) continue;
            try {
                field.setAccessible(true);
                Object tmp = field.get(obj);
                if (field.getType().isPrimitive()) { // 对于基本数据类型需要进行特殊的处理
                    ans = ans * PRIME + ((Number) tmp).longValue();
                    continue;
                }

                // 能够使用 Objects 计算 hashCode 的类，需要进行单独的处理
                if (isBasicType(c) || isEnum(c) || isMap(c)) {
                    ans = ans * PRIME + Objects.hashCode(tmp);
                    continue;
                }

                // 对于其余的情况，说明该属性字段是一个自定义对象，递归对每个字段进行处理
                ans = ans * PRIME + genHash(obj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return ans;
    }

    private final static Logger log = LoggerFactory.getLogger(DiffTool.class);

    @Resource
    private ObjectMapper mapper;

    @Test
    public void compareTest() {
        File rawFile = new File("src/test/resources/one.json");
        File newFile = new File("src/test/resources/two.json");
        try (
                Reader oldReader = new FileReader(rawFile);
                Reader newReader = new FileReader(newFile)
        ) {
//            ObjectMapper mapper = new ObjectMapper();
//            mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

//            Gson gson = new GsonBuilder().setPrettyPrinting().create();
//            Object oldObj = gson.fromJson(oldReader, Object.class);
//            Object newObj = gson.fromJson(newReader, Object.class);
//            System.out.println(gson.toJson(newObj));
//
//            System.out.println("=====================================");
//            Map<String, Node<Object>> diffMap = compare(oldObj, newObj);
//            System.out.println(gson.toJson(diffMap));
            Object oldObj = mapper.readValue(rawFile, Object.class);
            Object newObj = mapper.readValue(newFile, Object.class);
            System.out.println(
                    mapper.writerWithDefaultPrettyPrinter()
                            .writeValueAsString(oldObj)
            );

            Map<String, Node<Object>> diffMap = compare(oldObj, newObj);
            System.out.println(
                    mapper.writerWithDefaultPrettyPrinter()
                            .writeValueAsString(diffMap)
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        Object obj = mapper.readValue(, Object.class);
    }
}
