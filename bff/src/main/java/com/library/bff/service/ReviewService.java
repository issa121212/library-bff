package com.library.bff.service;

import com.library.bff.dto.ReviewRequest;
import com.library.bff.dto.ReviewResponse;

import java.util.List;
import java.util.UUID;

public interface ReviewService {
    ReviewResponse create(ReviewRequest request);
    ReviewResponse findById(UUID id);
    List<ReviewResponse> findAll();
    ReviewResponse update(UUID id, ReviewRequest request);
    void delete(UUID id);
}
