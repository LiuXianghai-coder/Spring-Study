package com.example.demo.entity;

/**
 * @author lxh
 */
public class SyntheticMethodDemo {
    class NestedClass {
        private String nestedField;
    }

    public String getNestedFiled() {
        return new NestedClass().nestedField;
    }

    public void setNestedField(String nestedField) {
        new NestedClass().nestedField = nestedField;
    }
}
