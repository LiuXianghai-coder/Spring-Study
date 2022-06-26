package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author xhliu
 * @create 2022-06-16-11:07
 **/
public class Order {
    private int id;
    private String orderName;
    private String orderDesc;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate orderCreatedDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderCreatedDateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
    }

    public LocalDate getOrderCreatedDate() {
        return orderCreatedDate;
    }

    public void setOrderCreatedDate(LocalDate orderCreatedDate) {
        this.orderCreatedDate = orderCreatedDate;
    }

    public LocalDateTime getOrderCreatedDateTime() {
        return orderCreatedDateTime;
    }

    public void setOrderCreatedDateTime(LocalDateTime orderCreatedDateTime) {
        this.orderCreatedDateTime = orderCreatedDateTime;
    }

    @Override
    public String toString() {
        return "Order [id=" + id + ", orderName=" + orderName + ", orderDesc=" + orderDesc + ", orderCreatedDate=" +
                orderCreatedDate + ", orderCreatedDateTime=" + orderCreatedDateTime + "]";
    }
}
