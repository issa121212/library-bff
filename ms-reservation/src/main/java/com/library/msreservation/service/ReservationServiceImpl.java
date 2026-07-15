package com.library.msreservation.service;

import lombok.extern.slf4j.Slf4j;

import com.library.msreservation.dto.ReservationRequest;
import com.library.msreservation.dto.ReservationResponse;
import com.library.msreservation.exception.ReservationNotFoundException;
import com.library.msreservation.model.Reservation;
import com.library.msreservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository repository;
    /**
     * Crea un nuevo registro en el sistema.
     */


    @Override
    public ReservationResponse create(ReservationRequest request) {
        log.info("Ejecutando método create");
        Reservation entity = Reservation.builder()
            .id(UUID.randomUUID())
            .bookId(request.bookId())
            .username(request.username())
            .reservationDate(java.time.LocalDateTime.now())
            .status("PENDING")
            .build();
        return toResponse(repository.save(entity));
    }
    /**
     * Busca un registro por su ID único.
     */


    @Override
    public ReservationResponse findById(UUID id) {
        log.info("Ejecutando método findById");
        return toResponse(repository.findById(id)
            .orElseThrow(() -> new ReservationNotFoundException(id)));
    }
    /**
     * Retorna la lista completa de todos los registros.
     */


    @Override
    public List<ReservationResponse> findAll() {
        log.info("Ejecutando método findAll");
        return repository.findAll().stream().map(this::toResponse).toList();
    }
    /**
     * Actualiza la información de un registro existente.
     */


    @Override
    public ReservationResponse update(UUID id, ReservationRequest request) {
        log.info("Ejecutando método update");
        Reservation entity = repository.findById(id)
            .orElseThrow(() -> new ReservationNotFoundException(id));
        entity.setBookId(request.bookId());
        entity.setUsername(request.username());
        return toResponse(repository.save(entity));
    }
    /**
     * Elimina de forma permanente un registro del sistema.
     */


    @Override
    public void delete(UUID id) {
        log.info("Ejecutando método delete");
        Reservation entity = repository.findById(id)
            .orElseThrow(() -> new ReservationNotFoundException(id));
        repository.delete(entity);
    }

    private ReservationResponse toResponse(Reservation entity) {
        return new ReservationResponse(entity.getId(), entity.getBookId(), entity.getUsername(), entity.getReservationDate(), entity.getStatus());
    }
}
