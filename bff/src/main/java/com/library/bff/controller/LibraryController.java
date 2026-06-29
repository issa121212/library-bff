package com.library.bff.controller;

import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.library.bff.dto.AuthorRequest;
import com.library.bff.dto.AuthorResponse;
import com.library.bff.dto.BookRequest;
import com.library.bff.dto.BookWithAuthorResponse;
import com.library.bff.service.AuthorService;
import com.library.bff.service.BookService;


@RestController
@RequestMapping("/api/library")
@RequiredArgsConstructor
public class LibraryController {

    private final BookService   bookService;
    private final AuthorService authorService;

    // ── LIBROS ──────────────────────────────────────────────────────────────

    @GetMapping("/books")
    public ResponseEntity<List<BookWithAuthorResponse>> getAllBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String category,
            @RequestParam(required = false, defaultValue = "false") boolean excludeAuthor) {
        return ResponseEntity.ok(bookService.findAll(title, category, excludeAuthor));
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<BookWithAuthorResponse> getBookById(@PathVariable UUID id) {
        return ResponseEntity.ok(bookService.findById(id));
    }

    @PostMapping("/books")
    public ResponseEntity<BookWithAuthorResponse> createBook(@Valid @RequestBody BookRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.create(request));
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<BookWithAuthorResponse> updateBook(
            @PathVariable UUID id,
            @Valid @RequestBody BookRequest request) {
        return ResponseEntity.ok(bookService.update(id, request));
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable UUID id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ── AUTORES ─────────────────────────────────────────────────────────────

    @GetMapping("/authors")
    public ResponseEntity<List<AuthorResponse>> getAllAuthors() {
        return ResponseEntity.ok(authorService.findAll());
    }

    @GetMapping("/authors/{id}")
    public ResponseEntity<AuthorResponse> getAuthorById(@PathVariable Long id) {
        return ResponseEntity.ok(authorService.findById(id));
    }

    @PostMapping("/authors")
    public ResponseEntity<AuthorResponse> createAuthor(@Valid @RequestBody AuthorRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authorService.create(request));
    }

    @PutMapping("/authors/{id}")
    public ResponseEntity<AuthorResponse> updateAuthor(
            @PathVariable Long id,
            @Valid @RequestBody AuthorRequest request) {
        return ResponseEntity.ok(authorService.update(id, request));
    }

    @DeleteMapping("/authors/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        authorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
