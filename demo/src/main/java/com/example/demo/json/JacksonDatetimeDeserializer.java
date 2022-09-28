package com.example.demo.json;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author lxh
 */
public class JacksonDatetimeDeserializer extends JsonDeserializer<LocalDateTime> {
    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JacksonException {
        String str = p.getValueAsString();
        String[] strs = str.split("-");
        return LocalDateTime.of(Integer.parseInt(strs[0]), Integer.parseInt(strs[1]),
                Integer.parseInt(strs[2]), 0, 0);
    }
}
