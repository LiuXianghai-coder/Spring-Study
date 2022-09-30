package com.example.demo.entity;

import com.example.demo.common.BackupInfo;

/**
 * @author xhliu
 **/
public class ProdInfo extends AbstractEntity implements BackupInfo {
    private String id;

    private String prodName;

    private String backUpId;

    @Override
    public String getRecordId() {
        return id;
    }

    @Override
    public String getBackUpId() {
        return backUpId;
    }

    @Override
    public void setBackUpId(String backupId) {
        this.backUpId = backupId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    @Override
    public String toString() {
        return "ProdInfo{" +
                "id='" + id + '\'' +
                ", prodName='" + prodName + '\'' +
                ", backUpId='" + backUpId + '\'' +
                super.toString() +
                '}';
    }
}
