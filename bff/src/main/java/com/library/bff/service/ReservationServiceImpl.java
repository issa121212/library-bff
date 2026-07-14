package com.library.bff.service;

import com.library.bff.client.ReservationClient;
import com.library.bff.dto.ReservationRequest;
import com.library.bff.dto.ReservationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationClient client;

    @Override
    public ReservationResponse create(ReservationRequest request) {
        return client.create(request);
    }

    @Override
    public ReservationResponse findById(UUID id) {
        return client.findById(id);
    }

    @Override
    public List<ReservationResponse> findAll() {
        return client.findAll();
    }

    @Override
    public ReservationResponse update(UUID id, ReservationRequest request) {
        return client.update(id, request);
    }

    @Override
    public void delete(UUID id) {
        client.delete(id);
    }
}
