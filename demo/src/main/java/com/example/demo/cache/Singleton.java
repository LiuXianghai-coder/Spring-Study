package com.example.demo.cache;

import java.io.ObjectStreamException;
import java.io.Serializable;

/**
 * @author lxh
 */
public class Singleton implements Serializable {

    private final static long serialVersionUID = 1L;

    private final static Singleton INSTANCE;

    static {
        INSTANCE = new Singleton();
    }

    private transient Object field = new Object();

    protected Object readResolve()
            throws ObjectStreamException {
        return INSTANCE;
    }

    private Singleton() {}

    public Singleton getInstance() {return INSTANCE;}
}
