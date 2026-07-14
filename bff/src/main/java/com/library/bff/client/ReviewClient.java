package com.library.bff.client;

import com.library.bff.dto.ReviewRequest;
import com.library.bff.dto.ReviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ReviewClient {

    private final RestClient restClient;

    @Value("${services.review.url}")
    private String serviceUrl;


    public ReviewResponse findById(UUID id) {
        return restClient.get()
            .uri(serviceUrl + "/api/review/" + id)
            .retrieve()
            .body(ReviewResponse.class);
    }

    public List<ReviewResponse> findAll() {
        return restClient.get()
            .uri(serviceUrl + "/api/review")
            .retrieve()
            .body(new ParameterizedTypeReference<List<ReviewResponse>>() {});
    }

    public ReviewResponse create(ReviewRequest request) {
        return restClient.post()
            .uri(serviceUrl + "/api/review")
            .contentType(MediaType.APPLICATION_JSON)
            .body(request)
            .retrieve()
            .body(ReviewResponse.class);
    }

    public ReviewResponse update(UUID id, ReviewRequest request) {
        return restClient.put()
            .uri(serviceUrl + "/api/review/" + id)
            .contentType(MediaType.APPLICATION_JSON)
            .body(request)
            .retrieve()
            .body(ReviewResponse.class);
    }

    public void delete(UUID id) {
        restClient.delete()
            .uri(serviceUrl + "/api/review/" + id)
            .retrieve()
            .toBodilessEntity();
    }
}
