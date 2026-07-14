package com.library.bff.client;

import com.library.bff.dto.BookCopyRequest;
import com.library.bff.dto.BookCopyResponse;
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
public class BookCopyClient {

    private final RestClient restClient;

    @Value("${services.inventory.url}")
    private String serviceUrl;


    public BookCopyResponse findById(UUID id) {
        return restClient.get()
            .uri(serviceUrl + "/api/book_copy/" + id)
            .retrieve()
            .body(BookCopyResponse.class);
    }

    public List<BookCopyResponse> findAll() {
        return restClient.get()
            .uri(serviceUrl + "/api/book_copy")
            .retrieve()
            .body(new ParameterizedTypeReference<List<BookCopyResponse>>() {});
    }

    public BookCopyResponse create(BookCopyRequest request) {
        return restClient.post()
            .uri(serviceUrl + "/api/book_copy")
            .contentType(MediaType.APPLICATION_JSON)
            .body(request)
            .retrieve()
            .body(BookCopyResponse.class);
    }

    public BookCopyResponse update(UUID id, BookCopyRequest request) {
        return restClient.put()
            .uri(serviceUrl + "/api/book_copy/" + id)
            .contentType(MediaType.APPLICATION_JSON)
            .body(request)
            .retrieve()
            .body(BookCopyResponse.class);
    }

    public void delete(UUID id) {
        restClient.delete()
            .uri(serviceUrl + "/api/book_copy/" + id)
            .retrieve()
            .toBodilessEntity();
    }
}
