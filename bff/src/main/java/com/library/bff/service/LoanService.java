package com.library.bff.service;

import com.library.bff.dto.LoanRequest;
import com.library.bff.dto.LoanResponse;

import java.util.List;
import java.util.UUID;

public interface LoanService {
    LoanResponse create(LoanRequest request);
    LoanResponse findById(UUID id);
    List<LoanResponse> findAll();
    LoanResponse update(UUID id, LoanRequest request);
    void delete(UUID id);
}
