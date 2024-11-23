package com.example.demo.tools;

import cn.hutool.core.util.IdcardUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author lxh
 */
public class IdCardTool {

    private static final int[] POWER = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};

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

    protected static char calCheckSum(String s) {
        int sum = 0;
        for (int i = 0; i < 17; ++i) {
            sum += (POWER[i]) * (s.charAt(i) - '0');
        }
        switch (sum % 11) {
            case 10:
                return '2';
            case 9:
                return '3';
            case 8:
                return '4';
            case 7:
                return '5';
            case 6:
                return '6';
            case 5:
                return '7';
            case 4:
                return '8';
            case 3:
                return '9';
            case 2:
                return 'X';
            case 1:
                return '0';
            case 0:
                return '1';
            default:
                throw new RuntimeException("非法的运行参数");
        }
    }

    public static void main(String[] args) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < 10000; ++i) {
            int year = random.nextInt(1970, 2024);
            int month = random.nextInt(1, 13);
            int day = random.nextInt(1, 29);
            String id = genIdCardNumber(year * 10000 + month * 100 + day);
            if (!IdcardUtil.isValidCard(id)) {
                System.out.println(id);
            }
        }
    }
}
