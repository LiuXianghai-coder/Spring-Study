package org.xhliu.springflowable;

import org.apache.commons.lang3.time.TimeZones;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author xhliu
 * @time 2022-02-21-下午10:06
 */
public class SimpleTest {
    @Test
    public void test() throws UnsupportedEncodingException {
        String text = "这是一个简单的 GBK 字符串";
        String res = new String(text.getBytes(UTF_8), UTF_8);
        System.out.println(res);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime time = LocalDateTime.now();
        System.out.println(time.getYear() + "-" + time.getMonthValue() + "-"
                + time.getDayOfMonth() + " " + time.getHour() + ":" + time.getMinute() + ":" + time.getSecond());
        System.out.println("max: " + formatter.format(time.with(TemporalAdjusters.lastDayOfMonth())));
        System.out.println("yesterday: " + formatter.format(time.minusDays(1)));

        LocalDateTime last = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
        ZoneId zoneId = ZoneId.systemDefault();
        System.out.println("minus=" + (time.atZone(zoneId).toEpochSecond() - last.atZone(zoneId).toEpochSecond()));

        Date date = new Date(0);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));

        System.out.println("date: " + format.format(date));

        var a = 1;
        System.out.println("a=" + a);
    }

    @Test
    public void  justTest() {
        String text = "a good   example";
        System.out.println(Arrays.toString(text.split("\\s+")));
    }
}
