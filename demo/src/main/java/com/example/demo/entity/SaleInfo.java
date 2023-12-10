package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.OffsetDateTime;

/**
 *@author lxh
 */
@Table(name = "sale_info")
public class SaleInfo {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "sale_id")
    private Long saleId;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "year")
    private OffsetDateTime year;

    public Long getId() {
        return id;
    }

    public Long getSaleId() {
        return saleId;
    }

    public Integer getAmount() {
        return amount;
    }

    public OffsetDateTime getYear() {
        return year;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSaleId(Long saleId) {
        this.saleId = saleId;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public void setYear(OffsetDateTime year) {
        this.year = year;
    }
}
