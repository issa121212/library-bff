package com.library.bff.service;

import com.library.bff.dto.ReservationRequest;
import com.library.bff.dto.ReservationResponse;

import java.util.List;
import java.util.UUID;

public interface ReservationService {
    ReservationResponse create(ReservationRequest request);
    ReservationResponse findById(UUID id);
    List<ReservationResponse> findAll();
    ReservationResponse update(UUID id, ReservationRequest request);
    void delete(UUID id);
}
