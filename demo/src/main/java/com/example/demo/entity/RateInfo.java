package com.example.demo.entity;

import com.google.common.base.Objects;

import java.io.Serializable;

/**
 * @author xhliu
 **/
public class RateInfo extends AbstractEntity implements Serializable {
    private long id;
    private String rateName;
    private String rateVal;
    private String backUpId;

    public String getBackUpId() {
        return backUpId;
    }

    public void setBackUpId(String backUpId) {
        this.backUpId = backUpId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRateName() {
        return rateName;
    }

    public void setRateName(String rateName) {
        this.rateName = rateName;
    }

    public String getRateVal() {
        return rateVal;
    }

    public void setRateVal(String rateVal) {
        this.rateVal = rateVal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RateInfo rateInfo = (RateInfo) o;
        return id == rateInfo.id && Objects.equal(rateName, rateInfo.rateName)
                && Objects.equal(rateVal, rateInfo.rateVal);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, rateName, rateVal);
    }

    @Override
    public String toString() {
        return "RateInfo{" +
                "id=" + id +
                ", rateName='" + rateName + '\'' +
                ", rateVal='" + rateVal + '\'' +
                ", backUpId='" + backUpId + '\'' +
                '}';
    }
}
