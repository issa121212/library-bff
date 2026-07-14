package com.library.bff.service;

import com.library.bff.client.ReviewClient;
import com.library.bff.dto.ReviewRequest;
import com.library.bff.dto.ReviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewClient client;

    @Override
    public ReviewResponse create(ReviewRequest request) {
        return client.create(request);
    }

    @Override
    public ReviewResponse findById(UUID id) {
        return client.findById(id);
    }

    @Override
    public List<ReviewResponse> findAll() {
        return client.findAll();
    }

    @Override
    public ReviewResponse update(UUID id, ReviewRequest request) {
        return client.update(id, request);
    }

    @Override
    public void delete(UUID id) {
        client.delete(id);
    }
}
