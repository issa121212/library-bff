package com.library.msreservation.service;

import com.library.msreservation.dto.ReservationRequest;
import com.library.msreservation.dto.ReservationResponse;

import java.util.List;
import java.util.UUID;

public interface ReservationService {
    ReservationResponse create(ReservationRequest request);
    ReservationResponse findById(UUID id);
    List<ReservationResponse> findAll();
    ReservationResponse update(UUID id, ReservationRequest request);
    void delete(UUID id);
}
