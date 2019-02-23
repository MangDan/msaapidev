package io.helidon.examples.book.service;

import java.util.List;

import io.helidon.examples.book.entity.Book;


public interface BookService {
    public List<Book> listAllBook(String title, Integer limit, Integer offset);
    public Book findBookByid(String id);
}