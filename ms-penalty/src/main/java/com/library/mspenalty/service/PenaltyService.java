package com.library.mspenalty.service;

import com.library.mspenalty.dto.PenaltyRequest;
import com.library.mspenalty.dto.PenaltyResponse;

import java.util.List;
import java.util.UUID;

public interface PenaltyService {
    PenaltyResponse create(PenaltyRequest request);
    PenaltyResponse findById(UUID id);
    List<PenaltyResponse> findAll();
    PenaltyResponse update(UUID id, PenaltyRequest request);
    void delete(UUID id);
    PenaltyResponse payPenalty(UUID id);
}
