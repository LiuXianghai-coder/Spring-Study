package com.example.demo.tools;

import cn.hutool.core.util.IdcardUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author lxh
 */
public class IdCardTool {

    private final static TreeMap<String, String> AREA_MAP = new TreeMap<>();

    static {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try (InputStream in = loader.getResourceAsStream("area_map.txt")) {
            StringBuilder sb = new StringBuilder();
            int len;
            byte[] buffer = new byte[4 * 1024];
            while ((len = in.read(buffer)) > 0) {
                sb.append(new String(buffer, 0, len));
            }
            String[] ss = sb.toString().split("\n");
            for (String s : ss) {
                String[] pair = s.split(",");
                if (pair[0] == null || pair[0].isEmpty()) continue;
                if (pair[1] == null || pair[1].isEmpty()) continue;
                AREA_MAP.put(pair[0], pair[1]);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String genIdCardNumber(int birth) {
        if (birth < 1000_0000) {
            throw new IllegalArgumentException("出生日期必须为 8 位");
        }

        ThreadLocalRandom random = ThreadLocalRandom.current();
        List<String> list = new ArrayList<>();
        for (Map.Entry<String, String> entry : AREA_MAP.entrySet()) {
            list.add(entry.getKey());
        }
        int index = random.nextInt(0, list.size());
        String key = list.get(index);
        StringBuilder sb = new StringBuilder();

        // 前六位
        sb.append(key);

        // 出生日期
        sb.append(birth);

        // 顺序编码
        sb.append(String.format("%2s", random.nextInt(0, 99))
                .replaceAll(" ", "0"));
        sb.append((random.nextInt() & 1)); // 随机设置为性别

        // 最后一位的校验码
        sb.append(calCheckSum(sb.toString()));
        return sb.toString();
    }

    protected static int calCheckSum(String s) {
        int sum = 0;
        for (int i = 0; i < 17; ++i) {
            sum = ((int) Math.pow(2, 17 - i) % 11) * (s.charAt(i) - '0');
        }
        int num = (12 - (sum % 11)) % 11;
        if (num < 10) return s.charAt(16) - '0';
        return 'X';
    }

    public static void main(String[] args) {
        String id1 = genIdCardNumber(19700805);
        System.out.println(id1);
        System.out.println(IdcardUtil.isValidCard(id1));;
    }
}
