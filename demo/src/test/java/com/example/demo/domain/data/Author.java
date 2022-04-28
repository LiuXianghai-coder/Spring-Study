package com.example.demo.domain.data;

/**
 * @author xhliu
 * @create 2022-04-28-13:31
 **/
public class Author {
    String name;

    int age;

    String describe;

    Education education;

    public Author(
            String name,
            int age,
            String describe,
            Education education
    ) {
        this.name = name;
        this.age = age;
        this.describe = describe;
        this.education = education;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Education getEducation() {
        return education;
    }

    public void setEducation(Education education) {
        this.education = education;
    }
}
