package io.helidon.examples.book.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import io.helidon.examples.book.entity.Book;
import io.helidon.examples.book.repository.BookRepository;

@ApplicationScoped
public class BookServiceImpl implements BookService {

    @Inject
    private BookRepository bookRepo;

    @Override
    public List<Book> listAllBook(String title, Integer offset, Integer limit) {
        return bookRepo.listAllBook(title, offset, limit);
    }

    @Override
    public Book findBookByid(String id) {
        return bookRepo.findBookByid(id);
    }
}