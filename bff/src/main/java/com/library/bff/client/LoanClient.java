package com.library.bff.client;

import com.library.bff.dto.LoanRequest;
import com.library.bff.dto.LoanResponse;
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
public class LoanClient {

    private final RestClient restClient;

    @Value("${services.loan.url}")
    private String serviceUrl;


    public LoanResponse findById(UUID id) {
        return restClient.get()
            .uri(serviceUrl + "/api/loan/" + id)
            .retrieve()
            .body(LoanResponse.class);
    }

    public List<LoanResponse> findAll() {
        return restClient.get()
            .uri(serviceUrl + "/api/loan")
            .retrieve()
            .body(new ParameterizedTypeReference<List<LoanResponse>>() {});
    }

    public LoanResponse create(LoanRequest request) {
        return restClient.post()
            .uri(serviceUrl + "/api/loan")
            .contentType(MediaType.APPLICATION_JSON)
            .body(request)
            .retrieve()
            .body(LoanResponse.class);
    }

    public LoanResponse update(UUID id, LoanRequest request) {
        return restClient.put()
            .uri(serviceUrl + "/api/loan/" + id)
            .contentType(MediaType.APPLICATION_JSON)
            .body(request)
            .retrieve()
            .body(LoanResponse.class);
    }

    public void delete(UUID id) {
        restClient.delete()
            .uri(serviceUrl + "/api/loan/" + id)
            .retrieve()
            .toBodilessEntity();
    }
}
