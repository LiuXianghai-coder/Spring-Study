package com.example.restdata.Entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: liuxianghai
 * Date: 2021/1/8
 * Time: 下午10:09
 */

@Data
@Entity
@Table(name = "book_info")
public class BookInfo {
    @Id
    @Column(name = "isbn")
    private String isbn;

    @Basic
    @Column(name = "book_name")
    private String bookName;

    @Basic
    @Column(name = "book_price")
    private Double bookPrice;
}

