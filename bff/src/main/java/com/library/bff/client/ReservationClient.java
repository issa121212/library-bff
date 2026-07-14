package com.library.bff.client;

import com.library.bff.dto.ReservationRequest;
import com.library.bff.dto.ReservationResponse;
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
public class ReservationClient {

    private final RestClient restClient;

    @Value("${services.reservation.url}")
    private String serviceUrl;


    public ReservationResponse findById(UUID id) {
        return restClient.get()
            .uri(serviceUrl + "/api/reservation/" + id)
            .retrieve()
            .body(ReservationResponse.class);
    }

    public List<ReservationResponse> findAll() {
        return restClient.get()
            .uri(serviceUrl + "/api/reservation")
            .retrieve()
            .body(new ParameterizedTypeReference<List<ReservationResponse>>() {});
    }

    public ReservationResponse create(ReservationRequest request) {
        return restClient.post()
            .uri(serviceUrl + "/api/reservation")
            .contentType(MediaType.APPLICATION_JSON)
            .body(request)
            .retrieve()
            .body(ReservationResponse.class);
    }

    public ReservationResponse update(UUID id, ReservationRequest request) {
        return restClient.put()
            .uri(serviceUrl + "/api/reservation/" + id)
            .contentType(MediaType.APPLICATION_JSON)
            .body(request)
            .retrieve()
            .body(ReservationResponse.class);
    }

    public void delete(UUID id) {
        restClient.delete()
            .uri(serviceUrl + "/api/reservation/" + id)
            .retrieve()
            .toBodilessEntity();
    }
}
