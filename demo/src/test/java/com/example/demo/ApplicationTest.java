package com.example.demo;

import com.example.demo.domain.Data;
import com.example.demo.domain.od.service.Observe;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.*;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author xhliu
 * @create 2022-04-12-16:00
 **/
@SpringBootTest
public class ApplicationTest {

    @Test
    public void test() {
//        String dateTxt = "Oct 12, 2019 3:39:52 PM";
//        DateFormat format = new SimpleDateFormat("MMMM d, yyyy HH:mm:ss a", Locale.ENGLISH);
//        Date date = format.parse(dateTxt);
//        System.out.println(date);
        Gson gson = new GsonBuilder().setPrettyPrinting()
                .create();
        try (
                Reader reader = new FileReader("src/test/resources/res.json")
        ) {
            Type type = new TypeToken<Map<String, Data>>(){}.getType();
            Map<String, Data> data = gson.fromJson(reader, type);
            System.out.println(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Resource
    private List<Observe> observes;

    @Test
    public void contextTest() {
        observes.forEach(obj -> obj.notify("Hello World"));
    }
}
