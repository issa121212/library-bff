package com.library.msloan.service;

import com.library.msloan.dto.LoanRequest;
import com.library.msloan.dto.LoanResponse;
import com.library.msloan.exception.LoanNotFoundException;
import com.library.msloan.model.Loan;
import com.library.msloan.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final LoanRepository repository;

    @Override
    public LoanResponse create(LoanRequest request) {
        Loan entity = Loan.builder()
            .id(UUID.randomUUID())
            .bookCopyId(request.bookCopyId())
            .username(request.username())
            .loanDate(java.time.LocalDateTime.now())
            .dueDate(java.time.LocalDateTime.now())
            .returnDate(java.time.LocalDateTime.now())
            .status("ACTIVE")
            .build();
        return toResponse(repository.save(entity));
    }

    @Override
    public LoanResponse findById(UUID id) {
        return toResponse(repository.findById(id)
            .orElseThrow(() -> new LoanNotFoundException(id)));
    }

    @Override
    public List<LoanResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public LoanResponse update(UUID id, LoanRequest request) {
        Loan entity = repository.findById(id)
            .orElseThrow(() -> new LoanNotFoundException(id));
        entity.setBookCopyId(request.bookCopyId());
        entity.setUsername(request.username());
        return toResponse(repository.save(entity));
    }

    @Override
    public void delete(UUID id) {
        Loan entity = repository.findById(id)
            .orElseThrow(() -> new LoanNotFoundException(id));
        repository.delete(entity);
    }

    private LoanResponse toResponse(Loan entity) {
        return new LoanResponse(entity.getId(), entity.getBookCopyId(), entity.getUsername(), entity.getLoanDate(), entity.getDueDate(), entity.getReturnDate(), entity.getStatus());
    }
}
