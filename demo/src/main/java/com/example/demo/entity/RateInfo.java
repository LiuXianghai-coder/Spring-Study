package com.example.demo.entity;

import com.example.demo.common.BackupInfo;
import com.google.common.base.Objects;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author xhliu
 **/
public class RateInfo
        extends AbstractEntity
        implements BackupInfo, Serializable {

    private final static Logger log = LoggerFactory.getLogger(RateInfo.class);

    @Getter
    private long id;
    private String rateName;
    @Getter
    private BigDecimal rateVal;
    private String backUpId;

    @Override
    public String getRecordId() {
        return String.valueOf(id);
    }

    @Override
    public String getBackUpId() {
        return backUpId;
    }

    @Override
    public void setBackUpId(String backupId) {
        this.backUpId = backupId;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setRateName(String rateName) {
        this.rateName = rateName;
    }

    public void setRateVal(BigDecimal rateVal) {
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
                super.toString() +
                '}';
    }
}
