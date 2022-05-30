package com.example.demo.domain.ed.entity;

import java.time.LocalDateTime;

public class EdProjectInfo extends ProjectInfo {
    private String fundFor;

    private String fundType;

    private LocalDateTime payDate;

    public static EdProjectInfo EXAMPLE = new EdProjectInfo();

    static {
        EXAMPLE.setProjId("DN00001");
        EXAMPLE.setName("xhliu");
        EXAMPLE.setUpdateTime(LocalDateTime.now());
        EXAMPLE.setFundFor("保险");
        EXAMPLE.setFundType("Invest");
        EXAMPLE.setPayDate(LocalDateTime.of(2021, 5, 26, 19, 0));
    }

    public String getFundFor() {
        return fundFor;
    }

    public void setFundFor(String fundFor) {
        this.fundFor = fundFor;
    }

    public String getFundType() {
        return fundType;
    }

    public void setFundType(String fundType) {
        this.fundType = fundType;
    }

    public LocalDateTime getPayDate() {
        return payDate;
    }

    public void setPayDate(LocalDateTime payDate) {
        this.payDate = payDate;
    }

    @Override
    public String toString() {
        return "EdProjectInfo{" +
                "fundFor='" + fundFor + '\'' +
                ", fundType='" + fundType + '\'' +
                ", payDate=" + payDate +
                '}';
    }
}
