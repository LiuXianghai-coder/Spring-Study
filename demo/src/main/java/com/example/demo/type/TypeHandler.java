package com.example.demo.type;

@FunctionalInterface
public interface TypeHandler<T, U> {

    U convert(T t);
}
