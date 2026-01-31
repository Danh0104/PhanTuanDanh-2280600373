package com.example.Bai2.service;

import com.example.Bai2.Model.Book;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class BookService {
    private List<Book> books = new ArrayList<>();
    private int nextId = 1;

    public List<Book> getAllBooks() {
        return books;
    }

    public void addBook(Book book) {
        book.setId(nextId++);
        books.add(book);
    }

    public Optional<Book> getBookById(int id) {
        return books.stream().filter(b -> b.getId() == id).findFirst();
    }

    public void updateBook(Book updatedBook) {
        getBookById(updatedBook.getId()).ifPresent(book -> {
            book.setTitle(updatedBook.getTitle());
            book.setAuthor(updatedBook.getAuthor());
        });
    }

    public void deleteBook(int id) {
        books.removeIf(book -> book.getId() == id);
    }
}