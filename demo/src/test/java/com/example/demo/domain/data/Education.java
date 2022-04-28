package com.example.demo.domain.data;

/**
 * @author xhliu
 * @create 2022-04-28-13:37
 **/
public class Education {
    String name;

    Level level;

    public Education(String name, Level level) {
        this.name = name;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }
}
