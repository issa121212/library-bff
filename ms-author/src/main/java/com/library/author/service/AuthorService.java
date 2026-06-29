package com.library.author.service;
import com.library.author.dto.AuthorRequest;
import com.library.author.dto.AuthorResponse;
import java.util.List;
public interface AuthorService {
    AuthorResponse create(AuthorRequest request);
    AuthorResponse findById(Long id);
    List<AuthorResponse> findAll();
    AuthorResponse update(Long id, AuthorRequest request);
    void delete(Long id);
}
