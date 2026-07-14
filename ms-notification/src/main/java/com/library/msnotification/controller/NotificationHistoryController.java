package com.library.msnotification.controller;

import com.library.msnotification.dto.NotificationHistoryRequest;
import com.library.msnotification.dto.NotificationHistoryResponse;
import com.library.msnotification.service.NotificationHistoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/notification_history")
@RequiredArgsConstructor
public class NotificationHistoryController {

    private final NotificationHistoryService service;

    @GetMapping
    public ResponseEntity<List<NotificationHistoryResponse>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationHistoryResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<NotificationHistoryResponse> create(@Valid @RequestBody NotificationHistoryRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotificationHistoryResponse> update(@PathVariable UUID id, @Valid @RequestBody NotificationHistoryRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
