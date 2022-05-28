package com.example.demo.entity;

/**
 * @author xhliu
 * @create 2022-05-24-21:51
 **/
public class NyPizza extends Pizza{
    public enum Size {SMALL, MEDIUM, LARGE}
    private final Size size;

    public static class NyBuilder extends Pizza.Builder<NyBuilder> {
        private final Size size;

        public NyBuilder(Size size) {this.size = size;}

        @Override
        NyPizza build() {return new NyPizza(this);}

        @Override
        protected NyBuilder self() {return this;}
    }

    NyPizza(NyBuilder builder) {
        super(builder);
        this.size = builder.size;
    }
}
