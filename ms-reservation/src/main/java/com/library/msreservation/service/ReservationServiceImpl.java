package com.library.msreservation.service;

import com.library.msreservation.dto.ReservationRequest;
import com.library.msreservation.dto.ReservationResponse;
import com.library.msreservation.exception.ReservationNotFoundException;
import com.library.msreservation.model.Reservation;
import com.library.msreservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository repository;

    @Override
    public ReservationResponse create(ReservationRequest request) {
        Reservation entity = Reservation.builder()
            .id(UUID.randomUUID())
            .bookId(request.bookId())
            .username(request.username())
            .reservationDate(java.time.LocalDateTime.now())
            .status("PENDING")
            .build();
        return toResponse(repository.save(entity));
    }

    @Override
    public ReservationResponse findById(UUID id) {
        return toResponse(repository.findById(id)
            .orElseThrow(() -> new ReservationNotFoundException(id)));
    }

    @Override
    public List<ReservationResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public ReservationResponse update(UUID id, ReservationRequest request) {
        Reservation entity = repository.findById(id)
            .orElseThrow(() -> new ReservationNotFoundException(id));
        entity.setBookId(request.bookId());
        entity.setUsername(request.username());
        return toResponse(repository.save(entity));
    }

    @Override
    public void delete(UUID id) {
        Reservation entity = repository.findById(id)
            .orElseThrow(() -> new ReservationNotFoundException(id));
        repository.delete(entity);
    }

    private ReservationResponse toResponse(Reservation entity) {
        return new ReservationResponse(entity.getId(), entity.getBookId(), entity.getUsername(), entity.getReservationDate(), entity.getStatus());
    }
}
