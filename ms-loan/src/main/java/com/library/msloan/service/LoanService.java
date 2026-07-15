package com.library.msloan.service;

import com.library.msloan.dto.LoanRequest;
import com.library.msloan.dto.LoanResponse;

import java.util.List;
import java.util.UUID;

public interface LoanService {
    LoanResponse create(LoanRequest request);
    LoanResponse findById(UUID id);
    List<LoanResponse> findAll();
    LoanResponse update(UUID id, LoanRequest request);
    void delete(UUID id);
    LoanResponse returnLoan(UUID id);
}
