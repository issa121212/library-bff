package com.library.book.repository;

import java.util.UUID;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.library.book.model.Book;

public interface BookRepository extends JpaRepository<Book, UUID> {
    List<Book> findByTitleContainingIgnoreCase(String title);
    List<Book> findByCategoryContainingIgnoreCase(String category);
    List<Book> findByAuthorId(Long authorId);
}
