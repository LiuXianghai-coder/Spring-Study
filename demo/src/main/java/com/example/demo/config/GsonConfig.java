package com.example.demo.config;

import com.example.demo.entity.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author lxh
 * @date 2022/6/19-下午9:32
 */
public class GsonConfig {
    public static class NormalDateSerializerAdapter
            implements JsonSerializer<Date>, JsonDeserializer<Date> {

        @Override
        public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
            /*
             * 尽管 SimpleDateFormat 是线程不安全的（网上铺天盖地都有的八股文），但是在使用的过程中
             * 中是一个 ”栈封闭“ 的状态，明显这项操作是线程安全的
             *
             * Tips: SimpleDateFormat 不是是线程安全的类，因为它在格式化日期的过程中修改了私有属性状态，
             *       并且没有使用任何同步手段来保证操作的有序性
             */
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return new JsonPrimitive(format.format(src));
        }

        @Override
        public Date deserialize(
                JsonElement json, Type typeOfT,
                JsonDeserializationContext context
        ) throws JsonParseException {
            return Date.from(Instant.parse(json.getAsString()));
        }
    }

    private static class LocalDateTimeAdapter
            implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {
        private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        @Override
        public LocalDateTime deserialize(
                JsonElement json, Type typeOfT,
                JsonDeserializationContext context
        ) throws JsonParseException {
            return LocalDateTime.parse(json.getAsString(),
                    dateTimeFormatter.withZone(ZoneId.systemDefault())
            );
        }

        @Override
        public JsonElement serialize(
                LocalDateTime src, Type typeOfSrc,
                JsonSerializationContext context
        ) {
            return new JsonPrimitive(dateTimeFormatter.format(src));
        }
    }

    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();

        Order order = new Order();
        order.setId(1);
        order.setOrderCreatedDate(LocalDate.now());
        order.setOrderCreatedDateTime(LocalDateTime.now());

        System.out.println(mapper.writeValueAsString(order));
        System.out.println(gson.toJson(order));
    }
}
