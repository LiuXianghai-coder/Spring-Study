package com.example.demo.domain.data;

/**
 * @author xhliu
 * @create 2022-04-28-13:28
 **/
public class Index {
    String chapterName;

    int page;

    public Index(String chapterName, int page) {
        this.chapterName = chapterName;
        this.page = page;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
