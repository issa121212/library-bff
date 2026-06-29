package com.library.bff.service;

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

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookClient   bookClient;
    private final AuthorClient authorClient;

    @Override
    public List<BookWithAuthorResponse> findAll(String title, String category, boolean excludeAuthor) {
        return bookClient.findAll(title, category).stream()
            .map(book -> compose(book, excludeAuthor))
            .toList();
    }

    @Override
    public BookWithAuthorResponse findById(UUID id) {
        return compose(bookClient.findById(id), false);
    }

    @Override
    public BookWithAuthorResponse create(BookRequest request) {
        return compose(bookClient.create(request), false);
    }

    @Override
    public BookWithAuthorResponse update(UUID id, BookRequest request) {
        return compose(bookClient.update(id, request), false);
    }

    @Override
    public void delete(UUID id) {
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

