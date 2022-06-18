package com.example.demo.domain.od.entity;

import com.example.demo.domain.od.common.AbstractEntity;
import com.example.demo.domain.od.common.Animal;

/**
 * @author lxh
 * @date 2022/6/18-下午8:16
 */
public class Bird
        extends AbstractEntity
        implements Animal {
    private String birdId;

    private String birdColor;

    private Double weight;

    private String birdType;

    public String getBirdId() {
        return birdId;
    }

    public void setBirdId(String birdId) {
        this.birdId = birdId;
    }

    public String getBirdColor() {
        return birdColor;
    }

    public void setBirdColor(String birdColor) {
        this.birdColor = birdColor;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getBirdType() {
        return birdType;
    }

    public void setBirdType(String birdType) {
        this.birdType = birdType;
    }

    @Override
    public String toString() {
        return "Bird{" +
                "birdId='" + birdId + '\'' +
                ", birdColor='" + birdColor + '\'' +
                ", weight=" + weight +
                ", birdType='" + birdType + '\'' +
                '}';
    }
}
