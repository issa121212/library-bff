package com.library.bff.client;

import com.library.bff.dto.NotificationHistoryRequest;
import com.library.bff.dto.NotificationHistoryResponse;
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
public class NotificationHistoryClient {

    private final RestClient restClient;

    @Value("${services.notification.url}")
    private String serviceUrl;


    public NotificationHistoryResponse findById(UUID id) {
        return restClient.get()
            .uri(serviceUrl + "/api/notification_history/" + id)
            .retrieve()
            .body(NotificationHistoryResponse.class);
    }

    public List<NotificationHistoryResponse> findAll() {
        return restClient.get()
            .uri(serviceUrl + "/api/notification_history")
            .retrieve()
            .body(new ParameterizedTypeReference<List<NotificationHistoryResponse>>() {});
    }

    public NotificationHistoryResponse create(NotificationHistoryRequest request) {
        return restClient.post()
            .uri(serviceUrl + "/api/notification_history")
            .contentType(MediaType.APPLICATION_JSON)
            .body(request)
            .retrieve()
            .body(NotificationHistoryResponse.class);
    }

    public NotificationHistoryResponse update(UUID id, NotificationHistoryRequest request) {
        return restClient.put()
            .uri(serviceUrl + "/api/notification_history/" + id)
            .contentType(MediaType.APPLICATION_JSON)
            .body(request)
            .retrieve()
            .body(NotificationHistoryResponse.class);
    }

    public void delete(UUID id) {
        restClient.delete()
            .uri(serviceUrl + "/api/notification_history/" + id)
            .retrieve()
            .toBodilessEntity();
    }
}
