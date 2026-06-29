package com.library.bff.service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.library.bff.client.AuthorClient;
import com.library.bff.dto.AuthorRequest;
import com.library.bff.dto.AuthorResponse;

// Integra Mockito con JUnit 5.
// Gracias a esta anotacion, los campos con @Mock se crean automaticamente antes de cada test.
// Sin esto, tendriamos que inicializar mocks manualmente.
@ExtendWith(MockitoExtension.class)
class AuthorServiceImplTest {

    @Mock
    private AuthorClient authorClient;

    @Test
    void findAll_shouldDelegateToClient() {
        // *** Test puro: sin Spring, sin IO. Prueba findAll instanciando el servicio manualmente.
        AuthorServiceImpl authorService = new AuthorServiceImpl(authorClient);
        AuthorResponse author = new AuthorResponse(1L, "Gabriel", "Col", 1927);
        when(authorClient.findAll()).thenReturn(List.of(author));

        List<AuthorResponse> result = authorService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Gabriel", result.get(0).name());
        verify(authorClient).findAll();
    }

    @Test
    void findById_shouldDelegateToClient() {
        // *** Test puro: sin Spring, sin IO. Prueba findById instanciando el servicio manualmente.
        AuthorServiceImpl authorService = new AuthorServiceImpl(authorClient);
        AuthorResponse author = new AuthorResponse(1L, "Gabriel", "Col", 1927);
        when(authorClient.findById(1L)).thenReturn(author);

        AuthorResponse result = authorService.findById(1L);

        assertNotNull(result);
        assertEquals("Gabriel", result.name());
        verify(authorClient).findById(1L);
    }

    @Test
    void create_shouldDelegateToClient() {
        // *** Test puro: sin Spring, sin IO. Prueba create instanciando el servicio manualmente.
        AuthorServiceImpl authorService = new AuthorServiceImpl(authorClient);
        AuthorRequest request = new AuthorRequest("Gabriel", "Col", 1927);
        AuthorResponse author = new AuthorResponse(1L, "Gabriel", "Col", 1927);
        when(authorClient.create(request)).thenReturn(author);

        AuthorResponse result = authorService.create(request);

        assertNotNull(result);
        assertEquals("Gabriel", result.name());
        verify(authorClient).create(request);
    }

    @Test
    void update_shouldDelegateToClient() {
        // *** Test puro: sin Spring, sin IO. Prueba update instanciando el servicio manualmente.
        AuthorServiceImpl authorService = new AuthorServiceImpl(authorClient);
        AuthorRequest request = new AuthorRequest("Gabriel Updated", "Col", 1927);
        AuthorResponse author = new AuthorResponse(1L, "Gabriel Updated", "Col", 1927);
        when(authorClient.update(1L, request)).thenReturn(author);

        AuthorResponse result = authorService.update(1L, request);

        assertNotNull(result);
        assertEquals("Gabriel Updated", result.name());
        verify(authorClient).update(1L, request);
    }

    @Test
    void delete_shouldDelegateToClient() {
        // *** Test puro: sin Spring, sin IO. Prueba delete instanciando el servicio manualmente.
        AuthorServiceImpl authorService = new AuthorServiceImpl(authorClient);
        authorService.delete(1L);
        verify(authorClient).delete(1L);
    }
}
