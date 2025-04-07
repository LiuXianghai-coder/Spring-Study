package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *@author lxh
 */
@Table(name = "goods_stock_info")
public class GoodsStockInfo {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "goods_id")
    private Long goodsId;

    @Column(name = "goods_name")
    private String goodsName;

    @Column(name = "stock")
    private Integer stock;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
