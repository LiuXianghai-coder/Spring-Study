package com.example.demo.tools;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import com.example.demo.entity.DemoData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.io.Resources;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author xhliu
 */
public class ExcelTool {

    static void read() {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream in = Resources.getResourceAsStream("data.xlsx")) {
            EasyExcel.read(in, DemoData.class, new PageReadListener<DemoData>(list -> {
                for (DemoData data : list) {
                    try {
                        System.out.println(mapper.writeValueAsString(data));
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }
            })).sheet().doRead();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        read();
    }
}
