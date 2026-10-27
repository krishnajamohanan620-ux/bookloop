package com.campus.bookexchange.repository;

import com.campus.bookexchange.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("SELECT b FROM Book b WHERE " +
           "(:title IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))) AND " +
           "(:course IS NULL OR LOWER(b.course) = LOWER(:course)) AND " +
           "(:semester IS NULL OR b.semester = :semester) " +
           "ORDER BY b.id DESC")
    List<Book> searchBooks(@Param("title") String title,
                           @Param("course") String course,
                           @Param("semester") Integer semester);
}
