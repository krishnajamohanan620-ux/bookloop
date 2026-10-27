package com.campus.bookexchange.service;

import com.campus.bookexchange.model.Book;
import com.campus.bookexchange.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> search(String title, String course, Integer semester) {
        String cleanTitle = (title == null || title.isBlank()) ? null : title.trim();
        String cleanCourse = (course == null || course.isBlank()) ? null : course.trim();
        return bookRepository.searchBooks(cleanTitle, cleanCourse, semester);
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public Book findById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }
}
