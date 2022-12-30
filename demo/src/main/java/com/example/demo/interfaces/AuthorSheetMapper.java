package com.example.demo.interfaces;

/**
 * @author lxh
 */
public class AuthorSheetMapper<Author> implements SheetMapper<Author> {

    @Override
    public void createSheet(Author author) {
        System.out.println(author);
    }
}
