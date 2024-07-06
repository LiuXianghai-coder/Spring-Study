package com.example.demo.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class TimeTools {

    public static Date parseDate(String str) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return format.parse(str);
        } catch (ParseException e) {
            // ignore....
        }

        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(str);
        } catch (ParseException e) {
            // ignore.....
        }

        throw new IllegalArgumentException("找不到合适的日期解析格式");
    }
}
