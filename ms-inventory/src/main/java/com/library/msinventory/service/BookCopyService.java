package com.library.msinventory.service;

import com.library.msinventory.dto.BookCopyRequest;
import com.library.msinventory.dto.BookCopyResponse;

import java.util.List;
import java.util.UUID;

public interface BookCopyService {
    BookCopyResponse create(BookCopyRequest request);
    BookCopyResponse findById(UUID id);
    List<BookCopyResponse> findAll();
    BookCopyResponse update(UUID id, BookCopyRequest request);
    void delete(UUID id);
}
