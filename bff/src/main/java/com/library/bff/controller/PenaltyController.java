package com.library.bff.controller;

import com.library.bff.dto.PenaltyRequest;
import com.library.bff.dto.PenaltyResponse;
import com.library.bff.service.PenaltyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/library/penaltys")
@RequiredArgsConstructor
public class PenaltyController {

    private final PenaltyService service;

    @GetMapping
    public ResponseEntity<List<PenaltyResponse>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PenaltyResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<PenaltyResponse> create(@Valid @RequestBody PenaltyRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PenaltyResponse> update(@PathVariable UUID id, @Valid @RequestBody PenaltyRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
