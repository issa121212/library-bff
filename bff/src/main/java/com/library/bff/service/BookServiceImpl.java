package com.library.bff.service;

import lombok.extern.slf4j.Slf4j;

import com.library.bff.client.AuthorClient;
import com.library.bff.client.BookClient;
import com.library.bff.dto.AuthorResponse;
import com.library.bff.dto.BookRequest;
import com.library.bff.dto.BookResponse;
import com.library.bff.dto.BookWithAuthorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookClient   bookClient;
    private final AuthorClient authorClient;
    /**
     * Retorna la lista completa de todos los registros.
     */


    @Override
    public List<BookWithAuthorResponse> findAll(String title, String category, boolean excludeAuthor) {
        log.info("Ejecutando método findAll");
        return bookClient.findAll(title, category).stream()
            .map(book -> compose(book, excludeAuthor))
            .toList();
    }
    /**
     * Busca un registro por su ID único.
     */


    @Override
    public BookWithAuthorResponse findById(UUID id) {
        log.info("Ejecutando método findById");
        return compose(bookClient.findById(id), false);
    }
    /**
     * Crea un nuevo registro en el sistema.
     */


    @Override
    public BookWithAuthorResponse create(BookRequest request) {
        log.info("Ejecutando método create");
        return compose(bookClient.create(request), false);
    }
    /**
     * Actualiza la información de un registro existente.
     */


    @Override
    public BookWithAuthorResponse update(UUID id, BookRequest request) {
        log.info("Ejecutando método update");
        return compose(bookClient.update(id, request), false);
    }
    /**
     * Elimina de forma permanente un registro del sistema.
     */


    @Override
    public void delete(UUID id) {
        log.info("Ejecutando método delete");
        bookClient.delete(id);
    }

    private BookWithAuthorResponse compose(BookResponse book, boolean excludeAuthor) {
        AuthorResponse author = null;
        if (!excludeAuthor) {
            try {
                author = authorClient.findById(book.authorId());
            } catch (org.springframework.web.client.RestClientResponseException e) {
                // Si el autor no existe o el servicio de autor falla, se retorna null en el campo del autor
                // para no romper la respuesta completa de los libros y mantener la independencia.
            }
        }
        return new BookWithAuthorResponse(book.id(), book.title(), book.category(), book.isbn(), author);
    }
}

