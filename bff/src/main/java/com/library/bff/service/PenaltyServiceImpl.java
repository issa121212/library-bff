package com.library.bff.service;

import com.library.bff.client.PenaltyClient;
import com.library.bff.dto.PenaltyRequest;
import com.library.bff.dto.PenaltyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PenaltyServiceImpl implements PenaltyService {

    private final PenaltyClient client;

    @Override
    public PenaltyResponse create(PenaltyRequest request) {
        return client.create(request);
    }

    @Override
    public PenaltyResponse findById(UUID id) {
        return client.findById(id);
    }

    @Override
    public List<PenaltyResponse> findAll() {
        return client.findAll();
    }

    @Override
    public PenaltyResponse update(UUID id, PenaltyRequest request) {
        return client.update(id, request);
    }

    /**
     * Elimina una multa en el BFF (Operación deshabilitada por negocio).
     *
     * @param id Identificador
     */
    @Override
    public void delete(UUID id) {
        client.delete(id);
    }

    /**
     * Registra el pago de una multa en el BFF.
     *
     * @param id Identificador de la multa
     * @return Multa pagada y actualizada
     */
    @Override
    public PenaltyResponse payPenalty(UUID id) {
        return client.payPenalty(id);
    }
}
