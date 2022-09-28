package com.example.demo.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author lxh
 */
public class JacksonDateTimeSerializer extends JsonSerializer<LocalDateTime> {
    @Override
    public void serialize(LocalDateTime time, JsonGenerator gen,
                          SerializerProvider serializers) throws IOException {
        gen.writeString(time.getYear() + "-" + time.getMonth() + "-" + time.getDayOfMonth());
    }
}
