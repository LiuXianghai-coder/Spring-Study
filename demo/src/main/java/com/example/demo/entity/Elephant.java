package com.example.demo.entity;

/**
 * @author xhliu2
 * @create 2021-09-29 9:13
 **/
public class Elephant {
    private volatile Elephant instance = null;

    public Elephant getInstance() {
        Elephant result = instance;
        if (result == null) {
            synchronized (this) {
                if (instance == null)
                    instance = result = new Elephant();
            }
        }

        return result;
    }
}
