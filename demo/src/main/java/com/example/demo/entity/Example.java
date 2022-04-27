package com.example.demo.entity;

import com.google.common.base.Objects;

import javax.persistence.*;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author xhliu2
 * @create 2021-09-06 16:16
 **/
@Entity
public class Example {
    private Integer id;
    private String name;
    private Integer age;
    private Integer colA;
    private Integer colB;
    private Integer colC;

    public Example(){}

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "age")
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Basic
    @Column(name = "col_a")
    public Integer getColA() {
        return colA;
    }

    public void setColA(Integer colA) {
        this.colA = colA;
    }

    @Basic
    @Column(name = "col_b")
    public Integer getColB() {
        return colB;
    }

    public void setColB(Integer colB) {
        this.colB = colB;
    }

    @Basic
    @Column(name = "col_c")
    public Integer getColC() {
        return colC;
    }

    public void setColC(Integer colC) {
        this.colC = colC;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Example example = (Example) o;
        return Objects.equal(id, example.id)
                && Objects.equal(name, example.name)
                && Objects.equal(age, example.age)
                && Objects.equal(colA, example.colA)
                && Objects.equal(colB, example.colB)
                && Objects.equal(colC, example.colC);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, age, colA, colB, colC);
    }

    @Override
    public String toString() {
        return "Example{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", colA=" + colA +
                ", colB=" + colB +
                ", colC=" + colC +
                '}';
    }

    final static int mod = (int) 1e7 + 7;

    public static Example randomExample() {
        Example example = new Example();
        Random random = ThreadLocalRandom.current();

        long name = random.nextLong();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 64; i+= 8) {
            int val = (int) ((name >> i) & 255);
            sb.append((char) (val % 26 + 'a'));
        }
        example.setName(sb.toString());
        example.setAge(Math.abs(random.nextInt()) % 40 + 10);
        example.setColA(Math.abs(random.nextInt()) % mod);
        example.setColB(Math.abs(random.nextInt()) % mod);
        example.setColC(Math.abs(random.nextInt()) % mod);

        return example;
    }
}
