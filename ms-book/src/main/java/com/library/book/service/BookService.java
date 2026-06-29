package com.library.book.service;

import java.util.List;
import java.util.UUID;

import com.library.book.dto.BookRequest;
import com.library.book.dto.BookResponse;


public interface BookService {
    BookResponse create(BookRequest request);
    BookResponse findById(UUID id);
    List<BookResponse> findAll(String title, String category);
    BookResponse update(UUID id, BookRequest request);
    void delete(UUID id);
}
