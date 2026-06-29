package com.library.bff.client;

import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.library.bff.dto.BookRequest;
import com.library.bff.dto.BookResponse;

@Component
@RequiredArgsConstructor
public class BookClient {

    private final RestClient restClient;

    @Value("${services.book.url}")
    private String bookServiceUrl;

    public BookResponse findById(UUID id) {
        return restClient.get()
            .uri(bookServiceUrl + "/api/books/" + id)
            .retrieve()
            .body(BookResponse.class);
    }

    public List<BookResponse> findAll(String title, String category) {
        String uri = bookServiceUrl + "/api/books";
        if (title != null && !title.isBlank())       uri += "?title=" + title;
        if (category != null && !category.isBlank()) uri += (uri.contains("?") ? "&" : "?") + "category=" + category;
        return restClient.get()
            .uri(uri)
            .retrieve()
            .body(new ParameterizedTypeReference<List<BookResponse>>() {});
    }

    public BookResponse create(BookRequest request) {
        return restClient.post()
            .uri(bookServiceUrl + "/api/books")
            .contentType(MediaType.APPLICATION_JSON)
            .body(request)
            .retrieve()
            .body(BookResponse.class);
    }

    public BookResponse update(UUID id, BookRequest request) {
        return restClient.put()
            .uri(bookServiceUrl + "/api/books/" + id)
            .contentType(MediaType.APPLICATION_JSON)
            .body(request)
            .retrieve()
            .body(BookResponse.class);
    }

    public void delete(UUID id) {
        restClient.delete()
            .uri(bookServiceUrl + "/api/books/" + id)
            .retrieve()
            .toBodilessEntity();
    }
}
