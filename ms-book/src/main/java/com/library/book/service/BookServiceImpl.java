package com.library.book.service;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.library.book.dto.BookRequest;
import com.library.book.dto.BookResponse;
import com.library.book.exception.BookNotFoundException;
import com.library.book.model.Book;
import com.library.book.repository.BookRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    /**
     * Crea un nuevo registro en el sistema.
     */


    @Override
    public BookResponse create(BookRequest request) {
        log.info("Ejecutando método create");
        Book book = Book.builder()
            .title(request.title())
            .authorId(request.authorId())
            .category(request.category())
            .isbn(request.isbn())
            .build();
        return toResponse(bookRepository.save(book));
    }
    /**
     * Busca un registro por su ID único.
     */


    @Override
    public BookResponse findById(UUID id) {
        log.info("Ejecutando método findById");
        return toResponse(bookRepository.findById(id)
            .orElseThrow(() -> new BookNotFoundException(id)));
    }
    /**
     * Retorna la lista completa de todos los registros.
     */


    @Override
    public List<BookResponse> findAll(String title, String category) {
        log.info("Ejecutando método findAll");
        if (title != null && !title.isBlank())
            return bookRepository.findByTitleContainingIgnoreCase(title).stream().map(this::toResponse).toList();
        if (category != null && !category.isBlank())
            return bookRepository.findByCategoryContainingIgnoreCase(category).stream().map(this::toResponse).toList();
        return bookRepository.findAll().stream().map(this::toResponse).toList();
    }
    /**
     * Actualiza la información de un registro existente.
     */


    @Override
    public BookResponse update(UUID id, BookRequest request) {
        log.info("Ejecutando método update");
        Book book = bookRepository.findById(id)
            .orElseThrow(() -> new BookNotFoundException(id));
        book.setTitle(request.title());
        book.setAuthorId(request.authorId());
        book.setCategory(request.category());
        book.setIsbn(request.isbn());
        return toResponse(bookRepository.save(book));
    }
    /**
     * Elimina de forma permanente un registro del sistema.
     */


    @Override
    public void delete(UUID id) {
        log.info("Ejecutando método delete");
        Book book = bookRepository.findById(id)
            .orElseThrow(() -> new BookNotFoundException(id));
        bookRepository.delete(book);
    }

    private BookResponse toResponse(Book b) {
        return new BookResponse(b.getId(), b.getTitle(), b.getAuthorId(), b.getCategory(), b.getIsbn());
    }
}
