package com.library.bff.client;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.library.bff.dto.AuthorRequest;
import com.library.bff.dto.AuthorResponse;

@Component
@RequiredArgsConstructor
public class AuthorClient {

    private final RestClient restClient;

    @Value("${services.author.url}")
    private String authorServiceUrl;

    public List<AuthorResponse> findAll() {
        return restClient.get()
            .uri(authorServiceUrl + "/api/authors")
            .retrieve()
            .body(new ParameterizedTypeReference<List<AuthorResponse>>() {});
    }

    public AuthorResponse findById(Long id) {
        return restClient.get()
            .uri(authorServiceUrl + "/api/authors/" + id)
            .retrieve()
            .body(AuthorResponse.class);
    }

    public AuthorResponse create(AuthorRequest request) {
        return restClient.post()
            .uri(authorServiceUrl + "/api/authors")
            .contentType(MediaType.APPLICATION_JSON)
            .body(request)
            .retrieve()
            .body(AuthorResponse.class);
    }

    public AuthorResponse update(Long id, AuthorRequest request) {
        return restClient.put()
            .uri(authorServiceUrl + "/api/authors/" + id)
            .contentType(MediaType.APPLICATION_JSON)
            .body(request)
            .retrieve()
            .body(AuthorResponse.class);
    }

    public void delete(Long id) {
        restClient.delete()
            .uri(authorServiceUrl + "/api/authors/" + id)
            .retrieve()
            .toBodilessEntity();
    }
}
