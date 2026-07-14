package com.library.mspenalty.service;

import com.library.mspenalty.dto.PenaltyRequest;
import com.library.mspenalty.dto.PenaltyResponse;
import com.library.mspenalty.exception.PenaltyNotFoundException;
import com.library.mspenalty.model.Penalty;
import com.library.mspenalty.repository.PenaltyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PenaltyServiceImpl implements PenaltyService {

    private final PenaltyRepository repository;

    @Override
    public PenaltyResponse create(PenaltyRequest request) {
        Penalty entity = Penalty.builder()
            .id(UUID.randomUUID())
            .loanId(request.loanId())
            .username(request.username())
            .amount(request.amount())
            .status("PENDING")
            .build();
        return toResponse(repository.save(entity));
    }

    @Override
    public PenaltyResponse findById(UUID id) {
        return toResponse(repository.findById(id)
            .orElseThrow(() -> new PenaltyNotFoundException(id)));
    }

    @Override
    public List<PenaltyResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public PenaltyResponse update(UUID id, PenaltyRequest request) {
        Penalty entity = repository.findById(id)
            .orElseThrow(() -> new PenaltyNotFoundException(id));
        entity.setLoanId(request.loanId());
        entity.setUsername(request.username());
        entity.setAmount(request.amount());
        return toResponse(repository.save(entity));
    }

    @Override
    public void delete(UUID id) {
        Penalty entity = repository.findById(id)
            .orElseThrow(() -> new PenaltyNotFoundException(id));
        repository.delete(entity);
    }

    private PenaltyResponse toResponse(Penalty entity) {
        return new PenaltyResponse(entity.getId(), entity.getLoanId(), entity.getUsername(), entity.getAmount(), entity.getStatus());
    }
}
