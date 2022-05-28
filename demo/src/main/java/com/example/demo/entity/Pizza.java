package com.example.demo.entity;

import java.util.EnumSet;
import java.util.Set;

/**
 * @author xhliu
 * @create 2022-05-24-21:37
 **/
public abstract class Pizza {
    public enum Topping {HAM, MUSHROOM, ONION, PEPPER, SAUSAGE}
    final Set<Topping> toppings;

    abstract static class Builder<T extends Builder<T>> {
        // 创建一个空的指定类型的枚举集合
        EnumSet<Topping> toppings = EnumSet.noneOf(Topping.class);
        public T addTopping(Topping topping) {
            toppings.add(topping);
            return self();
        }

        // 具体对象的构造过程
        abstract Pizza build();

        // 由子类重写该方法，返回当前对象
        protected abstract T self();
    }

    Pizza(Builder<?> builder) {
        // 使用保护性的clone 方法，使得产品对象最终是不可变对象
        toppings = builder.toppings.clone();
    }

    public static void main(String[] args) {
        NyPizza nyPizza = new NyPizza.NyBuilder(NyPizza.Size.MEDIUM)
                .addTopping(Topping.HAM).addTopping(Topping.MUSHROOM)
                .build();
        Calzone calzone = new Calzone.CalBuilder().sauceInside()
                .addTopping(Topping.SAUSAGE).addTopping(Topping.PEPPER)
                .build();
    }
}
