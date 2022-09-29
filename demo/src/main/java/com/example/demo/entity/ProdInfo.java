package com.example.demo.entity;

import com.example.demo.common.BackupInfo;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.code.ORDER;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author xhliu
 **/
@Table(name = "prod_info")
public class ProdInfo extends AbstractEntity implements BackupInfo {
    @Id
    @Column(name = "id")
    @KeySql(sql = "SELECT replace(uuid(), '-', '')", order = ORDER.BEFORE)
    private String id;

    @Column(name = "prod_name")
    private String prodName;

    @Column(name = "backup_id")
    private String backUpId;

    @Override
    public String getRecordId() {
        return id;
    }

    @Override
    public String getBackupId() {
        return backUpId;
    }

    @Override
    public void setBackupId(String backupId) {
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

    public String getBackUpId() {
        return backUpId;
    }

    public void setBackUpId(String backUpId) {
        this.backUpId = backUpId;
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
