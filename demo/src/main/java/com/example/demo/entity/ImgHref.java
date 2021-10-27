package com.example.demo.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author xhliu2
 * @create 2021-08-19 14:07
 **/
@Data
@Entity
@Table(name = "manhua_img")
public class ImgHref {
    @Id
    @Column(name = "img_id")
    private Long imgId;

    @Column(name = "chapter_id")
    private Integer chapterId;

    @Column(name = "chapter_img_id")
    private Integer chapterImgId;

    @Column(name = "img_href ")
    private String imgHref;
}
