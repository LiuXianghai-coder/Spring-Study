package com.example.demo.domain.od.entity;

import com.example.demo.domain.od.common.AbstractEntity;
import com.example.demo.domain.od.common.Animal;

/**
 * Mouse Entity
 *
 * @author lxh
 * @date 2022/6/18-下午8:07
 */
public class Mouse
        extends AbstractEntity
        implements Animal {
    private String mouseId; // mouse label

    private String mouseType; // mouse type

    private Integer coatColor; // 0-black, 1-brown, 2-yellow

    private Integer area; // mouse's area

    private Double pawLen; // mouse's paw length

    private Double tailLength;

    public String getMouseId() {
        return mouseId;
    }

    public void setMouseId(String mouseId) {
        this.mouseId = mouseId;
    }

    public String getMouseType() {
        return mouseType;
    }

    public void setMouseType(String mouseType) {
        this.mouseType = mouseType;
    }

    public Integer getCoatColor() {
        return coatColor;
    }

    public void setCoatColor(Integer coatColor) {
        this.coatColor = coatColor;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public Double getPawLen() {
        return pawLen;
    }

    public void setPawLen(Double pawLen) {
        this.pawLen = pawLen;
    }

    public Double getTailLength() {
        return tailLength;
    }

    public void setTailLength(Double tailLength) {
        this.tailLength = tailLength;
    }

    @Override
    public String toString() {
        return "Mouse{" +
                "mouseId='" + mouseId + '\'' +
                ", mouseType='" + mouseType + '\'' +
                ", coatColor=" + coatColor +
                ", area=" + area +
                ", pawLen=" + pawLen +
                ", tailLength=" + tailLength +
                '}';
    }
}
