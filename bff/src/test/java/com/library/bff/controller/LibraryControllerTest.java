package com.library.bff.controller;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.library.bff.dto.AuthorRequest;
import com.library.bff.dto.AuthorResponse;
import com.library.bff.dto.BookRequest;
import com.library.bff.dto.BookWithAuthorResponse;
import com.library.bff.service.AuthorService;
import com.library.bff.service.BookService;

// Integra Mockito con JUnit 5.
// Gracias a esta anotacion, los campos con @Mock se crean automaticamente antes de cada test.
// Sin esto, tendriamos que inicializar mocks manualmente.
@ExtendWith(MockitoExtension.class)
class LibraryControllerTest {

    @Mock
    private BookService bookService;

    @Mock
    private AuthorService authorService;

    @Test
    void getAllBooks_shouldReturnList() {
        // *** Este test prueba la consulta de todos los libros instanciando manualmente el controlador
        LibraryController libraryController = new LibraryController(bookService, authorService);
        BookWithAuthorResponse book = new BookWithAuthorResponse(UUID.randomUUID(), "Title", "Novela", "123-456", null);
        when(bookService.findAll("Title", "Novela", false)).thenReturn(List.of(book));

        ResponseEntity<List<BookWithAuthorResponse>> result = libraryController.getAllBooks("Title", "Novela", false);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(1, result.getBody().size());
        assertEquals("Title", result.getBody().get(0).title());
    }

    @Test
    void getBookById_shouldReturnBook() {
        // *** Este test prueba la obtención de un libro por ID instanciando manualmente el controlador
        LibraryController libraryController = new LibraryController(bookService, authorService);
        UUID uuid = UUID.randomUUID();
        BookWithAuthorResponse book = new BookWithAuthorResponse(uuid, "Title", "Novela", "123-456", null);
        when(bookService.findById(uuid)).thenReturn(book);

        ResponseEntity<BookWithAuthorResponse> result = libraryController.getBookById(uuid);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals("Title", result.getBody().title());
    }

    @Test
    void createBook_shouldReturnCreated() {
        // *** Este test prueba la creación de un nuevo libro instanciando manualmente el controlador
        LibraryController libraryController = new LibraryController(bookService, authorService);
        BookRequest request = new BookRequest("Title", 1L, "Novela", "123-456");
        BookWithAuthorResponse book = new BookWithAuthorResponse(UUID.randomUUID(), "Title", "Novela", "123-456", null);
        when(bookService.create(any(BookRequest.class))).thenReturn(book);

        ResponseEntity<BookWithAuthorResponse> result = libraryController.createBook(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals("Title", result.getBody().title());
    }

    @Test
    void updateBook_shouldReturnUpdated() {
        // *** Este test prueba la actualización de un libro instanciando manualmente el controlador
        LibraryController libraryController = new LibraryController(bookService, authorService);
        UUID uuid = UUID.randomUUID();
        BookRequest request = new BookRequest("Title Updated", 1L, "Novela", "123-456");
        BookWithAuthorResponse book = new BookWithAuthorResponse(uuid, "Title Updated", "Novela", "123-456", null);
        when(bookService.update(eq(uuid), any(BookRequest.class))).thenReturn(book);

        ResponseEntity<BookWithAuthorResponse> result = libraryController.updateBook(uuid, request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals("Title Updated", result.getBody().title());
    }

    @Test
    void deleteBook_shouldReturnNoContent() {
        // *** Este test prueba el borrado de un libro instanciando manualmente el controlador
        LibraryController libraryController = new LibraryController(bookService, authorService);
        UUID uuid = UUID.randomUUID();
        ResponseEntity<Void> result = libraryController.deleteBook(uuid);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(bookService).delete(uuid);
    }

    @Test
    void getAllAuthors_shouldReturnList() {
        // *** Este test prueba la consulta de todos los autores instanciando manualmente el controlador
        LibraryController libraryController = new LibraryController(bookService, authorService);
        AuthorResponse author = new AuthorResponse(1L, "Gabriel", "Col", 1927);
        when(authorService.findAll()).thenReturn(List.of(author));

        ResponseEntity<List<AuthorResponse>> result = libraryController.getAllAuthors();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(1, result.getBody().size());
        assertEquals("Gabriel", result.getBody().get(0).name());
    }

    @Test
    void getAuthorById_shouldReturnAuthor() {
        // *** Este test prueba la obtención de un autor por ID instanciando manualmente el controlador
        LibraryController libraryController = new LibraryController(bookService, authorService);
        AuthorResponse author = new AuthorResponse(1L, "Gabriel", "Col", 1927);
        when(authorService.findById(1L)).thenReturn(author);

        ResponseEntity<AuthorResponse> result = libraryController.getAuthorById(1L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals("Gabriel", result.getBody().name());
    }

    @Test
    void createAuthor_shouldReturnCreated() {
        // *** Este test prueba la creación de un autor instanciando manualmente el controlador
        LibraryController libraryController = new LibraryController(bookService, authorService);
        AuthorRequest request = new AuthorRequest("Gabriel", "Col", 1927);
        AuthorResponse author = new AuthorResponse(1L, "Gabriel", "Col", 1927);
        when(authorService.create(any(AuthorRequest.class))).thenReturn(author);

        ResponseEntity<AuthorResponse> result = libraryController.createAuthor(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals("Gabriel", result.getBody().name());
    }

    @Test
    void updateAuthor_shouldReturnUpdated() {
        // *** Este test prueba la actualización de un autor instanciando manualmente el controlador
        LibraryController libraryController = new LibraryController(bookService, authorService);
        AuthorRequest request = new AuthorRequest("Gabriel Updated", "Col", 1927);
        AuthorResponse author = new AuthorResponse(1L, "Gabriel Updated", "Col", 1927);
        when(authorService.update(eq(1L), any(AuthorRequest.class))).thenReturn(author);

        ResponseEntity<AuthorResponse> result = libraryController.updateAuthor(1L, request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals("Gabriel Updated", result.getBody().name());
    }

    @Test
    void deleteAuthor_shouldReturnNoContent() {
        // *** Este test prueba el borrado de un autor instanciando manualmente el controlador
        LibraryController libraryController = new LibraryController(bookService, authorService);
        ResponseEntity<Void> result = libraryController.deleteAuthor(1L);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(authorService).delete(1L);
    }
}
