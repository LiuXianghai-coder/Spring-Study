package com.example.demo.entity;

import lombok.Data;

/**
 * @author xhliu2
 * @create 2021-10-14 16:25
 **/
@Data
public class Person {
    private String personName;
    private String personGender;
    private Integer age;

    private static class Builder {
        private String personName;
        private String personGender;
        private Integer age;

        public Builder personName(String personName) {
            this.personName = personName;
            return this;
        }

        public Builder personGender(String personGender) {
            this.personGender = personGender;
            return this;
        }

        public Builder age(int age) {
            this.age = age;
            return this;
        }

        public Person builder() {
            return new Person(this);
        }
    }

    private Person(Builder builder) {
        this.personName = builder.personName;
        this.personGender = builder.personGender;
        this.age = builder.age;
    }

    public static Person defaultPerson() {
        Builder builder = new Builder();
        return builder.age(21)
                .personName("XianghaiLiu")
                .personGender("Man")
                .builder();
    }
}
