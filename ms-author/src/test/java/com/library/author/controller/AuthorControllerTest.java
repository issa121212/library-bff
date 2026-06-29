package com.library.author.controller;

import java.util.List;

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

import com.library.author.dto.AuthorRequest;
import com.library.author.dto.AuthorResponse;
import com.library.author.service.AuthorService;

// Integra Mockito con JUnit 5.
// Gracias a esta anotacion, los campos con @Mock se crean automaticamente antes de cada test.
// Sin esto, tendriamos que inicializar mocks manualmente.
@ExtendWith(MockitoExtension.class)
class AuthorControllerTest {

    @Mock
    private AuthorService authorService;

    @Test
    void findAll_shouldReturnOkResponseEntityWithListBody() {
        // *** Este test prueba la consulta de todos los autores instanciando manualmente el controlador
        AuthorController authorController = new AuthorController(authorService);
        AuthorResponse gabriel = new AuthorResponse(1L, "Gabriel García Márquez", "Colombiana", 1927);
        when(authorService.findAll()).thenReturn(List.of(gabriel));

        ResponseEntity<List<AuthorResponse>> result = authorController.findAll();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(1, result.getBody().size());
        assertEquals("Gabriel García Márquez", result.getBody().get(0).name());
    }

    @Test
    void findById_shouldReturnOkResponse() {
        // *** Este test prueba la obtención de un autor por su ID instanciando manualmente el controlador
        AuthorController authorController = new AuthorController(authorService);
        AuthorResponse orwell = new AuthorResponse(2L, "George Orwell", "Británica", 1903);
        when(authorService.findById(2L)).thenReturn(orwell);

        ResponseEntity<AuthorResponse> result = authorController.findById(2L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals("George Orwell", result.getBody().name());
    }

    @Test
    void create_shouldReturnCreatedResponse() {
        // *** Este test prueba la creación de un nuevo autor instanciando manualmente el controlador
        AuthorController authorController = new AuthorController(authorService);
        AuthorResponse neruda = new AuthorResponse(3L, "Pablo Neruda", "Chilena", 1904);
        when(authorService.create(any(AuthorRequest.class))).thenReturn(neruda);

        ResponseEntity<AuthorResponse> result = authorController.create(
            new AuthorRequest("Pablo Neruda", "Chilena", 1904)
        );

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals("Pablo Neruda", result.getBody().name());
    }

    @Test
    void update_shouldReturnOkResponse() {
        // *** Este test prueba la actualización de un autor existente instanciando manualmente el controlador
        AuthorController authorController = new AuthorController(authorService);
        AuthorResponse updatedNeruda = new AuthorResponse(3L, "Pablo Neruda Updated", "Chilena", 1904);
        when(authorService.update(eq(3L), any(AuthorRequest.class))).thenReturn(updatedNeruda);

        ResponseEntity<AuthorResponse> result = authorController.update(
            3L, new AuthorRequest("Pablo Neruda Updated", "Chilena", 1904)
        );

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals("Pablo Neruda Updated", result.getBody().name());
    }

    @Test
    void delete_shouldReturnNoContent() {
        // *** Este test prueba el borrado de un autor por ID instanciando manualmente el controlador
        AuthorController authorController = new AuthorController(authorService);
        ResponseEntity<Void> result = authorController.delete(4L);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(authorService).delete(4L);
    }
}
