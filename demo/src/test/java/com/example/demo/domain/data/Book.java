package com.example.demo.domain.data;

import java.util.List;
import java.util.Set;

/**
 * @author xhliu
 * @create 2022-04-28-13:27
 **/
public class Book {
    List<Index> indexList;

    String bookName;

    Set<Author> authors;

    short flag;

    public Book(
            List<Index> indexList,
            String bookName,
            Set<Author> authors
    ) {
        this.indexList = indexList;
        this.bookName = bookName;
        this.authors = authors;
    }

    public short getFlag() {
        return flag;
    }

    public void setFlag(short flag) {
        this.flag = flag;
    }

    public List<Index> getIndexList() {
        return indexList;
    }

    public void setIndexList(List<Index> indexList) {
        this.indexList = indexList;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }
}
