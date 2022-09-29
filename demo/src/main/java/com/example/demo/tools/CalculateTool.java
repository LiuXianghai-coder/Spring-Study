package com.example.demo.tools;

import com.example.demo.common.IfExtract;
import com.example.demo.entity.Book;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

/**
 * @author lxh
 * @date 2022/8/9-下午10:51
 */
@IfExtract(name = "CalTool")
public class CalculateTool {
    public long multiply(int x, int y) {
        return (long) x * y;
    }

    private long add(int x, int y) {
        return x + y;
    }


    static String parseStr(Book book) {
        return book.getAuthor();
    }

    public static void main(String[] args) {
        String str1 = "第一个";
        String str2 = "三幅图";
        str1 = new String(str1.getBytes(StandardCharsets.UTF_8), Charset.forName("GB2312"));
        str2 = new String(str2.getBytes(StandardCharsets.UTF_8), Charset.forName("GB2312"));
        char[] arr1 = str1.toCharArray();
        char[] arr2 = str2.toCharArray();

        for (char ch : arr1) {
            System.out.print(Integer.toHexString(ch) + " ");
        }
        System.out.println();
        for (char ch : arr2) {
            System.out.print(Integer.toHexString(ch) + " ");
        }
        System.out.println();

        System.out.println(str1.compareTo(str2));
    }
}
