package com.library.bff.service;

import com.library.bff.client.AuthorClient;
import com.library.bff.dto.AuthorRequest;
import com.library.bff.dto.AuthorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorClient authorClient;

    @Override
    public List<AuthorResponse> findAll() {
        return authorClient.findAll();
    }

    @Override
    public AuthorResponse findById(Long id) {
        return authorClient.findById(id);
    }

    @Override
    public AuthorResponse create(AuthorRequest request) {
        return authorClient.create(request);
    }

    @Override
    public AuthorResponse update(Long id, AuthorRequest request) {
        return authorClient.update(id, request);
    }

    @Override
    public void delete(Long id) {
        authorClient.delete(id);
    }
}
