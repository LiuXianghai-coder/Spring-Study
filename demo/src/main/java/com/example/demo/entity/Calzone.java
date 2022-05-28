package com.example.demo.entity;

/**
 * @author xhliu
 * @create 2022-05-24-21:55
 **/
public class Calzone extends Pizza{
    private final boolean sauceInside;

    public static class CalBuilder extends Pizza.Builder<CalBuilder> {
        private boolean sauceInside = false;

        public CalBuilder sauceInside() {
            sauceInside = true; return self();
        }

        @Override
        Calzone build() {return new Calzone(this);}

        @Override
        protected CalBuilder self() {return this;}
    }

    Calzone(CalBuilder builder) {
        super(builder);
        this.sauceInside = builder.sauceInside;
    }
}
