package com.library.msreview.service;

import com.library.msreview.dto.ReviewRequest;
import com.library.msreview.dto.ReviewResponse;
import com.library.msreview.exception.ReviewNotFoundException;
import com.library.msreview.model.Review;
import com.library.msreview.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository repository;

    @Override
    public ReviewResponse create(ReviewRequest request) {
        Review entity = Review.builder()
            .id(UUID.randomUUID())
            .bookId(request.bookId())
            .username(request.username())
            .rating(request.rating())
            .comment(request.comment())
            .build();
        return toResponse(repository.save(entity));
    }

    @Override
    public ReviewResponse findById(UUID id) {
        return toResponse(repository.findById(id)
            .orElseThrow(() -> new ReviewNotFoundException(id)));
    }

    @Override
    public List<ReviewResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public ReviewResponse update(UUID id, ReviewRequest request) {
        Review entity = repository.findById(id)
            .orElseThrow(() -> new ReviewNotFoundException(id));
        entity.setBookId(request.bookId());
        entity.setUsername(request.username());
        entity.setRating(request.rating());
        entity.setComment(request.comment());
        return toResponse(repository.save(entity));
    }

    @Override
    public void delete(UUID id) {
        Review entity = repository.findById(id)
            .orElseThrow(() -> new ReviewNotFoundException(id));
        repository.delete(entity);
    }

    private ReviewResponse toResponse(Review entity) {
        return new ReviewResponse(entity.getId(), entity.getBookId(), entity.getUsername(), entity.getRating(), entity.getComment());
    }
}
