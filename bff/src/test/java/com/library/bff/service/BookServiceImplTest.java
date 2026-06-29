package com.library.bff.service;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClientResponseException;

import com.library.bff.client.AuthorClient;
import com.library.bff.client.BookClient;
import com.library.bff.dto.AuthorResponse;
import com.library.bff.dto.BookRequest;
import com.library.bff.dto.BookResponse;
import com.library.bff.dto.BookWithAuthorResponse;

// Integra Mockito con JUnit 5.
// Gracias a esta anotacion, los campos con @Mock se crean automaticamente antes de cada test.
// Sin esto, tendriamos que inicializar mocks manualmente.
@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookClient bookClient;

    @Mock
    private AuthorClient authorClient;

    @Test
    void findAll_shouldReturnBooksWithAuthorWhenNotExcluded() {
        // *** Este test prueba el listado completo de libros con la información del autor compuesta de manera normal
        BookServiceImpl bookService = new BookServiceImpl(bookClient, authorClient);
        UUID bookId = UUID.randomUUID();
        BookResponse book = new BookResponse(bookId, "Cien años de soledad", 1L, "Novela", "978-0-06-088328-7");
        AuthorResponse author = new AuthorResponse(1L, "Gabriel García Márquez", "Colombiana", 1927);

        when(bookClient.findAll(null, null)).thenReturn(List.of(book));
        when(authorClient.findById(1L)).thenReturn(author);

        List<BookWithAuthorResponse> result = bookService.findAll(null, null, false);

        assertEquals(1, result.size());
        assertEquals("Cien años de soledad", result.get(0).title());
        assertNotNull(result.get(0).author());
        assertEquals("Gabriel García Márquez", result.get(0).author().name());

        verify(authorClient).findById(1L);
    }

    @Test
    void findAll_shouldReturnBooksWithNullAuthorWhenExcluded() {
        // *** Este test prueba la exclusión opcional del autor en el listado de libros para optimización de llamadas de red
        BookServiceImpl bookService = new BookServiceImpl(bookClient, authorClient);
        UUID bookId = UUID.randomUUID();
        BookResponse book = new BookResponse(bookId, "Cien años de soledad", 1L, "Novela", "978-0-06-088328-7");

        when(bookClient.findAll(null, null)).thenReturn(List.of(book));

        List<BookWithAuthorResponse> result = bookService.findAll(null, null, true);

        assertEquals(1, result.size());
        assertEquals("Cien años de soledad", result.get(0).title());
        assertNull(result.get(0).author());

        verify(authorClient, never()).findById(any());
    }

    @Test
    void findAll_shouldBeResilientWhenAuthorServiceFails() {
        // *** Este test prueba la resiliencia y tolerancia a fallos del BFF cuando el servicio de autores falla arrojando una excepción HTTP
        BookServiceImpl bookService = new BookServiceImpl(bookClient, authorClient);
        UUID bookId = UUID.randomUUID();
        BookResponse book = new BookResponse(bookId, "Cien años de soledad", 1L, "Novela", "978-0-06-088328-7");

        when(bookClient.findAll(null, null)).thenReturn(List.of(book));
        when(authorClient.findById(1L)).thenThrow(
            new RestClientResponseException("Author service down", 503, "Service Unavailable", null, null, null)
        );

        List<BookWithAuthorResponse> result = bookService.findAll(null, null, false);

        assertEquals(1, result.size());
        assertEquals("Cien años de soledad", result.get(0).title());
        assertNull(result.get(0).author());

        verify(authorClient).findById(1L);
    }

    @Test
    void findById_shouldReturnBookWithAuthor() {
        // *** Este test prueba la obtención de un libro específico y la composición exitosa con sus datos de autor
        BookServiceImpl bookService = new BookServiceImpl(bookClient, authorClient);
        UUID bookId = UUID.randomUUID();
        BookResponse book = new BookResponse(bookId, "Cien años de soledad", 1L, "Novela", "978-0-06-088328-7");
        AuthorResponse author = new AuthorResponse(1L, "Gabriel García Márquez", "Colombiana", 1927);

        when(bookClient.findById(bookId)).thenReturn(book);
        when(authorClient.findById(1L)).thenReturn(author);

        BookWithAuthorResponse result = bookService.findById(bookId);

        assertNotNull(result);
        assertEquals("Cien años de soledad", result.title());
        assertEquals("Gabriel García Márquez", result.author().name());
    }

    @Test
    void create_shouldReturnCreatedBookWithAuthor() {
        // *** Este test prueba la creación del libro y la posterior consulta para enriquecer el objeto final con el autor
        BookServiceImpl bookService = new BookServiceImpl(bookClient, authorClient);
        UUID bookId = UUID.randomUUID();
        BookRequest request = new BookRequest("Cien años de soledad", 1L, "Novela", "978-0-06-088328-7");
        BookResponse book = new BookResponse(bookId, "Cien años de soledad", 1L, "Novela", "978-0-06-088328-7");
        AuthorResponse author = new AuthorResponse(1L, "Gabriel García Márquez", "Colombiana", 1927);

        when(bookClient.create(any(BookRequest.class))).thenReturn(book);
        when(authorClient.findById(1L)).thenReturn(author);

        BookWithAuthorResponse result = bookService.create(request);

        assertNotNull(result);
        assertEquals("Cien años de soledad", result.title());
        assertEquals("Gabriel García Márquez", result.author().name());
    }

    @Test
    void update_shouldReturnUpdatedBookWithAuthor() {
        // *** Este test prueba la modificación de un libro existente delegando al cliente y componiendo el autor asociado
        BookServiceImpl bookService = new BookServiceImpl(bookClient, authorClient);
        UUID bookId = UUID.randomUUID();
        BookRequest request = new BookRequest("Cien años de soledad", 1L, "Novela", "978-0-06-088328-7");
        BookResponse book = new BookResponse(bookId, "Cien años de soledad", 1L, "Novela", "978-0-06-088328-7");
        AuthorResponse author = new AuthorResponse(1L, "Gabriel García Márquez", "Colombiana", 1927);

        when(bookClient.update(eq(bookId), any(BookRequest.class))).thenReturn(book);
        when(authorClient.findById(1L)).thenReturn(author);

        BookWithAuthorResponse result = bookService.update(bookId, request);

        assertNotNull(result);
        assertEquals("Cien años de soledad", result.title());
        assertEquals("Gabriel García Márquez", result.author().name());
    }

    @Test
    void delete_shouldInvokeBookClient() {
        // *** Este test prueba que la acción de borrado se delegue correctamente al cliente de libros externo
        BookServiceImpl bookService = new BookServiceImpl(bookClient, authorClient);
        UUID bookId = UUID.randomUUID();

        bookService.delete(bookId);

        verify(bookClient).delete(bookId);
    }
}
