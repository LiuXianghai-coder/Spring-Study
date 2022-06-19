package com.example.demo.domain.od.entity;

import com.example.demo.domain.od.common.AbstractEntity;
import com.example.demo.domain.od.common.Animal;

/**
 * @author lxh
 * @date 2022/6/18-下午8:14
 */
public class Fish
        extends AbstractEntity
        implements Animal {
    private String fishId;

    private String fishType;

    private String color;

    private String area;

    private Double swimSpeed;

    public String getFishId() {
        return fishId;
    }

    public void setFishId(String fishId) {
        this.fishId = fishId;
    }

    public String getFishType() {
        return fishType;
    }

    public void setFishType(String fishType) {
        this.fishType = fishType;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Double getSwimSpeed() {
        return swimSpeed;
    }

    public void setSwimSpeed(Double swimSpeed) {
        this.swimSpeed = swimSpeed;
    }

    @Override
    public String toString() {
        return "Fish{" +
                "fishId='" + fishId + '\'' +
                ", fishType='" + fishType + '\'' +
                ", color='" + color + '\'' +
                ", area='" + area + '\'' +
                ", swimSpeed=" + swimSpeed +
                '}';
    }
}
