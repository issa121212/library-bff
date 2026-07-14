package com.library.bff.client;

import com.library.bff.dto.PenaltyRequest;
import com.library.bff.dto.PenaltyResponse;
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
public class PenaltyClient {

    private final RestClient restClient;

    @Value("${services.penalty.url}")
    private String serviceUrl;


    public PenaltyResponse findById(UUID id) {
        return restClient.get()
            .uri(serviceUrl + "/api/penalty/" + id)
            .retrieve()
            .body(PenaltyResponse.class);
    }

    public List<PenaltyResponse> findAll() {
        return restClient.get()
            .uri(serviceUrl + "/api/penalty")
            .retrieve()
            .body(new ParameterizedTypeReference<List<PenaltyResponse>>() {});
    }

    public PenaltyResponse create(PenaltyRequest request) {
        return restClient.post()
            .uri(serviceUrl + "/api/penalty")
            .contentType(MediaType.APPLICATION_JSON)
            .body(request)
            .retrieve()
            .body(PenaltyResponse.class);
    }

    public PenaltyResponse update(UUID id, PenaltyRequest request) {
        return restClient.put()
            .uri(serviceUrl + "/api/penalty/" + id)
            .contentType(MediaType.APPLICATION_JSON)
            .body(request)
            .retrieve()
            .body(PenaltyResponse.class);
    }

    public void delete(UUID id) {
        restClient.delete()
            .uri(serviceUrl + "/api/penalty/" + id)
            .retrieve()
            .toBodilessEntity();
    }
}
