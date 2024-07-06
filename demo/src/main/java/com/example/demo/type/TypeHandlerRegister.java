package com.example.demo.type;

import com.example.demo.tools.TimeTools;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TypeHandlerRegister {

    private final Map<Class<?>, Map<Class<?>, TypeHandler<?, ?>>> typeRegister = new ConcurrentHashMap<>();

    {
        register(String.class, BigDecimal.class, BigDecimal::new);
        register(String.class, Integer.class, Integer::parseInt);
        register(String.class, Double.class, Double::parseDouble);
        register(String.class, Date.class, TimeTools::parseDate);
        register(String.class, String.class, String::valueOf);
    }

    public TypeHandlerRegister() {
    }

    public <T, U> void register(Class<T> tClazz, Class<U> uClazz, TypeHandler<T, U> handler) {
        typeRegister.computeIfAbsent(tClazz, key -> new HashMap<>());
        typeRegister.get(tClazz).put(uClazz, handler);
    }

    @SuppressWarnings("unchecked")
    public <T, U> TypeHandler<T, U> getHandler(Class<T> tClazz, Class<U> uClazz) {
        return (TypeHandler<T, U>) typeRegister.get(tClazz).get(uClazz);
    }
}
