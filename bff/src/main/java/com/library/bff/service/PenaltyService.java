package com.library.bff.service;

import com.library.bff.dto.PenaltyRequest;
import com.library.bff.dto.PenaltyResponse;

import java.util.List;
import java.util.UUID;

public interface PenaltyService {
    PenaltyResponse create(PenaltyRequest request);
    PenaltyResponse findById(UUID id);
    List<PenaltyResponse> findAll();
    PenaltyResponse update(UUID id, PenaltyRequest request);
    void delete(UUID id);
}
