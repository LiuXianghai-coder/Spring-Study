package com.example.demo.interfaces;

/**
 * @author lxh
 */
public class BookSheetMapper<Book> implements SheetMapper<Book> {

    @Override
    public void createSheet(Book book) {
        System.out.println(book);
    }
}
