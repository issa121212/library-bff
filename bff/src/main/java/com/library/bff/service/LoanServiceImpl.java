package com.library.bff.service;

import com.library.bff.client.LoanClient;
import com.library.bff.dto.LoanRequest;
import com.library.bff.dto.LoanResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final LoanClient client;

    @Override
    public LoanResponse create(LoanRequest request) {
        return client.create(request);
    }

    @Override
    public LoanResponse findById(UUID id) {
        return client.findById(id);
    }

    @Override
    public List<LoanResponse> findAll() {
        return client.findAll();
    }

    @Override
    public LoanResponse update(UUID id, LoanRequest request) {
        return client.update(id, request);
    }

    @Override
    public void delete(UUID id) {
        client.delete(id);
    }
}
