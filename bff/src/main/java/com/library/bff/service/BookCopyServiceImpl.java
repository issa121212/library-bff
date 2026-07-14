package com.library.bff.service;

import com.library.bff.client.BookCopyClient;
import com.library.bff.dto.BookCopyRequest;
import com.library.bff.dto.BookCopyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookCopyServiceImpl implements BookCopyService {

    private final BookCopyClient client;

    @Override
    public BookCopyResponse create(BookCopyRequest request) {
        return client.create(request);
    }

    @Override
    public BookCopyResponse findById(UUID id) {
        return client.findById(id);
    }

    @Override
    public List<BookCopyResponse> findAll() {
        return client.findAll();
    }

    @Override
    public BookCopyResponse update(UUID id, BookCopyRequest request) {
        return client.update(id, request);
    }

    @Override
    public void delete(UUID id) {
        client.delete(id);
    }
}
