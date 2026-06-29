package com.library.author.service;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.library.author.dto.AuthorRequest;
import com.library.author.dto.AuthorResponse;
import com.library.author.exception.AuthorNotFoundException;
import com.library.author.model.Author;
import com.library.author.repository.AuthorRepository;

// Integra Mockito con JUnit 5.
// Gracias a esta anotacion, los campos con @Mock se crean automaticamente antes de cada test.
// Sin esto, tendriamos que inicializar mocks manualmente.
@ExtendWith(MockitoExtension.class)
class AuthorServiceImplTest {

    @Mock
    private AuthorRepository authorRepository;

    @Test
    void findById_shouldReturnAuthorWhenExists() {
        // *** Test puro: sin Spring, sin IO. Prueba findById instanciando el servicio manualmente.
        AuthorServiceImpl authorService = new AuthorServiceImpl(authorRepository);
        Author entity = entity(1L, "Gabriel García Márquez", "Colombiana", 1927);
        when(authorRepository.findById(1L)).thenReturn(Optional.of(entity));

        AuthorResponse result = authorService.findById(1L);

        assertEquals(1L, result.id());
        assertEquals("Gabriel García Márquez", result.name());
        assertEquals("Colombiana", result.nationality());
        assertEquals(1927, result.birthYear());
    }

    @Test
    void findById_shouldThrowNotFoundWhenMissing() {
        // *** Test puro: sin Spring, sin IO. Prueba findById lanzando AuthorNotFoundException.
        AuthorServiceImpl authorService = new AuthorServiceImpl(authorRepository);
        when(authorRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(
            AuthorNotFoundException.class,
            () -> authorService.findById(99L)
        );
    }

    @Test
    void findAll_shouldReturnAllAuthors() {
        // *** Test puro: sin Spring, sin IO. Prueba obtener todos los autores instanciando el servicio manualmente.
        AuthorServiceImpl authorService = new AuthorServiceImpl(authorRepository);
        Author gabriel = entity(1L, "Gabriel García Márquez", "Colombiana", 1927);
        Author orwell = entity(2L, "George Orwell", "Británica", 1903);
        when(authorRepository.findAll()).thenReturn(List.of(gabriel, orwell));

        List<AuthorResponse> result = authorService.findAll();

        assertEquals(2, result.size());
        assertEquals("Gabriel García Márquez", result.get(0).name());
        assertEquals("George Orwell", result.get(1).name());
    }

    @Test
    void create_shouldSaveAndReturnDto() {
        // *** Test puro: sin Spring, sin IO. Prueba la creación de un autor instanciando el servicio manualmente.
        AuthorServiceImpl authorService = new AuthorServiceImpl(authorRepository);
        when(authorRepository.save(any(Author.class))).thenAnswer(invocation -> {
            Author toSave = invocation.getArgument(0);
            toSave.setId(5L);
            return toSave;
        });

        AuthorResponse result = authorService.create(new AuthorRequest(
            "Pablo Neruda",
            "Chilena",
            1904
        ));

        assertEquals(5L, result.id());
        assertEquals("Pablo Neruda", result.name());
        assertEquals("Chilena", result.nationality());
        assertEquals(1904, result.birthYear());

        ArgumentCaptor<Author> captor = ArgumentCaptor.forClass(Author.class);
        verify(authorRepository).save(captor.capture());
        assertEquals("Pablo Neruda", captor.getValue().getName());
    }

    @Test
    void update_shouldUpdateAndReturnDto() {
        // *** Test puro: sin Spring, sin IO. Prueba actualizar un autor existente instanciando el servicio manualmente.
        AuthorServiceImpl authorService = new AuthorServiceImpl(authorRepository);
        Author entity = entity(3L, "Pablo Neruda", "Chilena", 1904);
        when(authorRepository.findById(3L)).thenReturn(Optional.of(entity));
        when(authorRepository.save(any(Author.class))).thenAnswer(invocation -> invocation.getArgument(0));

        AuthorResponse result = authorService.update(3L, new AuthorRequest(
            "Pablo Neruda Updated",
            "Chilena",
            1904
        ));

        assertEquals(3L, result.id());
        assertEquals("Pablo Neruda Updated", result.name());

        verify(authorRepository).save(entity);
    }

    @Test
    void update_shouldThrowNotFoundWhenMissing() {
        // *** Test puro: sin Spring, sin IO. Prueba actualizar un autor inexistente lanzando excepción.
        AuthorServiceImpl authorService = new AuthorServiceImpl(authorRepository);
        when(authorRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(
            AuthorNotFoundException.class,
            () -> authorService.update(99L, new AuthorRequest("Name", "Nationality", 2000))
        );
    }

    @Test
    void delete_shouldDeleteWhenExists() {
        // *** Test puro: sin Spring, sin IO. Prueba la eliminación exitosa instanciando el servicio manualmente.
        AuthorServiceImpl authorService = new AuthorServiceImpl(authorRepository);
        Author entity = entity(4L, "Jorge Luis Borges", "Argentina", 1899);
        when(authorRepository.findById(4L)).thenReturn(Optional.of(entity));

        authorService.delete(4L);

        verify(authorRepository).delete(entity);
    }

    @Test
    void delete_shouldThrowNotFoundWhenMissing() {
        // *** Test puro: sin Spring, sin IO. Prueba eliminar un autor inexistente lanzando excepción.
        AuthorServiceImpl authorService = new AuthorServiceImpl(authorRepository);
        when(authorRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(
            AuthorNotFoundException.class,
            () -> authorService.delete(99L)
        );
    }

    private Author entity(Long id, String name, String nationality, Integer birthYear) {
        return Author.builder()
            .id(id)
            .name(name)
            .nationality(nationality)
            .birthYear(birthYear)
            .build();
    }
}
