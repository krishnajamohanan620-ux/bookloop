package com.campus.bookexchange.repository;

import com.campus.bookexchange.model.BookRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRequestRepository extends JpaRepository<BookRequest, Long> {
}
