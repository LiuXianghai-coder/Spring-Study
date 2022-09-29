package com.example.demo.entity;

import com.example.demo.common.BackupInfo;
import com.google.common.base.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author xhliu
 **/
public class RateInfo extends AbstractEntity implements BackupInfo, Serializable {

    private final static Logger log = LoggerFactory.getLogger(RateInfo.class);

    private long id;
    private String rateName;
    private BigDecimal rateVal;
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

    @Override
    public String getRecordId() {
        return String.valueOf(id);
    }

    @Override
    public String getBackupId() {
        return backUpId;
    }

    @Override
    public void setBackupId(String backupId) {
        this.backUpId = backupId;
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

    public BigDecimal getRateVal() {
        return rateVal;
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
