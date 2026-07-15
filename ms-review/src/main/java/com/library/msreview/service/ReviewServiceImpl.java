package com.library.msreview.service;

import lombok.extern.slf4j.Slf4j;

import com.library.msreview.dto.ReviewRequest;
import com.library.msreview.dto.ReviewResponse;
import com.library.msreview.exception.ReviewNotFoundException;
import com.library.msreview.model.Review;
import com.library.msreview.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository repository;
    /**
     * Crea un nuevo registro en el sistema.
     */


    @Override
    public ReviewResponse create(ReviewRequest request) {
        log.info("Ejecutando método create");
        Review entity = Review.builder()
            .id(UUID.randomUUID())
            .bookId(request.bookId())
            .username(request.username())
            .rating(request.rating())
            .comment(request.comment())
            .build();
        return toResponse(repository.save(entity));
    }
    /**
     * Busca un registro por su ID único.
     */


    @Override
    public ReviewResponse findById(UUID id) {
        log.info("Ejecutando método findById");
        return toResponse(repository.findById(id)
            .orElseThrow(() -> new ReviewNotFoundException(id)));
    }
    /**
     * Retorna la lista completa de todos los registros.
     */


    @Override
    public List<ReviewResponse> findAll() {
        log.info("Ejecutando método findAll");
        return repository.findAll().stream().map(this::toResponse).toList();
    }
    /**
     * Actualiza la información de un registro existente.
     */


    @Override
    public ReviewResponse update(UUID id, ReviewRequest request) {
        log.info("Ejecutando método update");
        Review entity = repository.findById(id)
            .orElseThrow(() -> new ReviewNotFoundException(id));
        entity.setBookId(request.bookId());
        entity.setUsername(request.username());
        entity.setRating(request.rating());
        entity.setComment(request.comment());
        return toResponse(repository.save(entity));
    }
    /**
     * Elimina de forma permanente un registro del sistema.
     */


    @Override
    public void delete(UUID id) {
        log.info("Ejecutando método delete");
        Review entity = repository.findById(id)
            .orElseThrow(() -> new ReviewNotFoundException(id));
        repository.delete(entity);
    }

    private ReviewResponse toResponse(Review entity) {
        return new ReviewResponse(entity.getId(), entity.getBookId(), entity.getUsername(), entity.getRating(), entity.getComment());
    }
}
