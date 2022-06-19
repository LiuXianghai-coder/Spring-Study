package com.example.demo.config;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.time.Instant;
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
}
