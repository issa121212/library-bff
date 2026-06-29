package com.library.bff.service;

import com.library.bff.dto.AuthorRequest;
import com.library.bff.dto.AuthorResponse;

import java.util.List;

public interface AuthorService {
    List<AuthorResponse> findAll();
    AuthorResponse findById(Long id);
    AuthorResponse create(AuthorRequest request);
    AuthorResponse update(Long id, AuthorRequest request);
    void delete(Long id);
}
