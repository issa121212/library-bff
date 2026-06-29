package com.library.book.controller;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.library.book.dto.BookRequest;
import com.library.book.dto.BookResponse;
import com.library.book.service.BookService;

// Integra Mockito con JUnit 5.
// Gracias a esta anotacion, los campos con @Mock se crean automaticamente antes de cada test.
// Sin esto, tendriamos que inicializar mocks manualmente.
@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    @Mock
    private BookService bookService;

    @Test
    void findAll_shouldReturnOkResponseEntityWithListBody() {
        // *** Este test prueba la consulta de todos los libros instanciando manualmente el controlador
        BookController bookController = new BookController(bookService);
        UUID bookId = UUID.randomUUID();
        BookResponse book = new BookResponse(bookId, "Cien años de soledad", 1L, "Novela", "978-0-06-088328-7");
        when(bookService.findAll(any(), any())).thenReturn(List.of(book));

        ResponseEntity<List<BookResponse>> result = bookController.findAll(null, null);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(1, result.getBody().size());
        assertEquals("Cien años de soledad", result.getBody().get(0).title());
    }

    @Test
    void findById_shouldReturnOkResponse() {
        // *** Este test prueba la obtención de un libro por su ID instanciando manualmente el controlador
        BookController bookController = new BookController(bookService);
        UUID bookId = UUID.randomUUID();
        BookResponse book = new BookResponse(bookId, "1984", 2L, "Distopía", "978-0-451-52493-5");
        when(bookService.findById(bookId)).thenReturn(book);

        ResponseEntity<BookResponse> result = bookController.findById(bookId);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals("1984", result.getBody().title());
    }

    @Test
    void create_shouldReturnCreatedResponse() {
        // *** Este test prueba la creación de un nuevo libro instanciando manualmente el controlador
        BookController bookController = new BookController(bookService);
        UUID bookId = UUID.randomUUID();
        BookResponse book = new BookResponse(bookId, "El otoño del patriarca", 1L, "Novela", "978-0-063-011418-7");
        when(bookService.create(any(BookRequest.class))).thenReturn(book);

        ResponseEntity<BookResponse> result = bookController.create(
            new BookRequest("El otoño del patriarca", 1L, "Novela", "978-0-063-011418-7")
        );

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals("El otoño del patriarca", result.getBody().title());
    }

    @Test
    void update_shouldReturnOkResponse() {
        // *** Este test prueba la actualización de un libro existente instanciando manualmente el controlador
        BookController bookController = new BookController(bookService);
        UUID bookId = UUID.randomUUID();
        BookResponse updatedBook = new BookResponse(bookId, "Cien años de soledad Updated", 1L, "Novela", "978-0-063-011418-7");
        when(bookService.update(eq(bookId), any(BookRequest.class))).thenReturn(updatedBook);

        ResponseEntity<BookResponse> result = bookController.update(
            bookId, new BookRequest("Cien años de soledad Updated", 1L, "Novela", "978-0-063-011418-7")
        );

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals("Cien años de soledad Updated", result.getBody().title());
    }

    @Test
    void delete_shouldReturnNoContent() {
        // *** Este test prueba el borrado de un libro por ID instanciando manualmente el controlador
        BookController bookController = new BookController(bookService);
        UUID bookId = UUID.randomUUID();
        ResponseEntity<Void> result = bookController.delete(bookId);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(bookService).delete(bookId);
    }
}
