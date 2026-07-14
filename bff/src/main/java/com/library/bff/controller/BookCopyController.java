package com.library.bff.controller;

import com.library.bff.dto.BookCopyRequest;
import com.library.bff.dto.BookCopyResponse;
import com.library.bff.service.BookCopyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/library/inventorys")
@RequiredArgsConstructor
public class BookCopyController {

    private final BookCopyService service;

    @GetMapping
    public ResponseEntity<List<BookCopyResponse>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookCopyResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<BookCopyResponse> create(@Valid @RequestBody BookCopyRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookCopyResponse> update(@PathVariable UUID id, @Valid @RequestBody BookCopyRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
