package com.example.demo.entity;

/**
 * @author xhliu
 * @create 2022-05-24-15:50
 **/
public class Book {
    private final long isbn;        // 必需
    private final String name ;     // 必需
    private final double price;     // 可选
    private final String author;    // 可选
    private final String publisher; // 可选
    private final String des;       // 可选

    private Book(Builder builder) {
        this.isbn = builder.isbn;
        this.name = builder.name;
        this.price = builder.price;
        this.author = builder.author;
        this.publisher = builder.publisher;
        this.des = builder.des;
    }

    public static class Builder {
        // 必需的参数
        private long isbn;
        private String name;

        // 可选参数
        private double price;
        private String author;
        private String publisher;
        private String des;

        public Builder(long isbn, String name) {
            this.isbn = isbn; this.name = name;
        }

        public Builder price(double price) {
            this.price = price;
            return this;
        }

        public Builder author(String author) {
            this.author = author;
            return this;
        }

        public Builder publisher(String publisher) {
            this.publisher = publisher;
            return this;
        }

        public Builder des(String des) {
            this.des = des;
            return this;
        }

        public Book build() {
            return new Book(this);
        }
    }

    public static void main(String[] args) {
        Book book = new Builder(9780201563177L, "APUE")
                .author("Stevens").publisher("Person")
                .price(106.5).des("")
                .build();
    }
}
