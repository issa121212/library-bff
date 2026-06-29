package com.library.bff.exception;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientResponseException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @SuppressWarnings("rawtypes")
    @ExceptionHandler(RestClientResponseException.class)
    public ResponseEntity<Map<String, Object>> handleRestClientResponse(RestClientResponseException ex) {
        String errorMsg = ex.getMessage();
        try {
            Map body = ex.getResponseBodyAs(Map.class);
            if (body != null && body.containsKey("error")) {
                errorMsg = (String) body.get("error");
            }
        } catch (Exception e) {
            // Ignorar si no se puede parsear
        }
        
        return ResponseEntity.status(ex.getStatusCode()).body(Map.of(
            "timestamp", LocalDateTime.now().toString(),
            "status", ex.getStatusCode().value(),
            "error", errorMsg
        ));
    }
}
