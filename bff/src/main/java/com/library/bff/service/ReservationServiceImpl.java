package com.library.bff.service;

import lombok.extern.slf4j.Slf4j;

import com.library.bff.client.ReservationClient;
import com.library.bff.dto.ReservationRequest;
import com.library.bff.dto.ReservationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationClient client;
    /**
     * Crea un nuevo registro en el sistema.
     */


    @Override
    public ReservationResponse create(ReservationRequest request) {
        log.info("Ejecutando método create");
        return client.create(request);
    }
    /**
     * Busca un registro por su ID único.
     */


    @Override
    public ReservationResponse findById(UUID id) {
        log.info("Ejecutando método findById");
        return client.findById(id);
    }
    /**
     * Retorna la lista completa de todos los registros.
     */


    @Override
    public List<ReservationResponse> findAll() {
        log.info("Ejecutando método findAll");
        return client.findAll();
    }
    /**
     * Actualiza la información de un registro existente.
     */


    @Override
    public ReservationResponse update(UUID id, ReservationRequest request) {
        log.info("Ejecutando método update");
        return client.update(id, request);
    }
    /**
     * Elimina de forma permanente un registro del sistema.
     */


    @Override
    public void delete(UUID id) {
        log.info("Ejecutando método delete");
        client.delete(id);
    }
}
