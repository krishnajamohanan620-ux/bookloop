package com.campus.bookexchange.service;

import com.campus.bookexchange.model.BookRequest;
import com.campus.bookexchange.repository.BookRequestRepository;
import org.springframework.stereotype.Service;

@Service
public class BookRequestService {
    private final BookRequestRepository repository;

    public BookRequestService(BookRequestRepository repository) {
        this.repository = repository;
    }

    public BookRequest save(BookRequest request) {
        return repository.save(request);
    }
}
