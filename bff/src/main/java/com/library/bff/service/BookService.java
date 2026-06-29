package com.library.bff.service;

import com.library.bff.dto.BookRequest;
import com.library.bff.dto.BookWithAuthorResponse;

import java.util.List;
import java.util.UUID;

public interface BookService {
    List<BookWithAuthorResponse> findAll(String title, String category, boolean excludeAuthor);
    BookWithAuthorResponse findById(UUID id);
    BookWithAuthorResponse create(BookRequest request);
    BookWithAuthorResponse update(UUID id, BookRequest request);
    void delete(UUID id);
}
