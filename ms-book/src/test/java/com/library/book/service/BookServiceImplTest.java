package com.library.book.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

import com.library.book.dto.BookRequest;
import com.library.book.dto.BookResponse;
import com.library.book.exception.BookNotFoundException;
import com.library.book.model.Book;
import com.library.book.repository.BookRepository;

// Integra Mockito con JUnit 5.
// Gracias a esta anotacion, los campos con @Mock se crean automaticamente antes de cada test.
// Sin esto, tendriamos que inicializar mocks manualmente.
@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Test
    void findById_shouldReturnBookWhenExists() {
        // *** Test puro: sin Spring, sin IO. Prueba findById instanciando el servicio manualmente.
        BookServiceImpl bookService = new BookServiceImpl(bookRepository);
        UUID bookId = UUID.randomUUID();
        Book entity = entity(bookId, "1984", 2L, "Distopía", "978-0-451-52493-5");
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(entity));

        BookResponse result = bookService.findById(bookId);

        assertEquals(bookId, result.id());
        assertEquals("1984", result.title());
        assertEquals(2L, result.authorId());
        assertEquals("Distopía", result.category());
    }

    @Test
    void findById_shouldThrowNotFoundWhenMissing() {
        // *** Test puro: sin Spring, sin IO. Prueba findById lanzando BookNotFoundException.
        BookServiceImpl bookService = new BookServiceImpl(bookRepository);
        UUID bookId = UUID.randomUUID();
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(
            BookNotFoundException.class,
            () -> bookService.findById(bookId)
        );
    }

    @Test
    void findAll_shouldReturnAllBooksNoFilter() {
        // *** Test puro: sin Spring, sin IO. Prueba findAll sin filtros instanciando el servicio manualmente.
        BookServiceImpl bookService = new BookServiceImpl(bookRepository);
        Book b1 = entity(UUID.randomUUID(), "Cien años de soledad", 1L, "Novela", "978-0-06-088328-7");
        Book b2 = entity(UUID.randomUUID(), "1984", 2L, "Distopía", "978-0-451-52493-5");
        when(bookRepository.findAll()).thenReturn(List.of(b1, b2));

        List<BookResponse> result = bookService.findAll(null, null);

        assertEquals(2, result.size());
    }

    @Test
    void findAll_shouldFilterByTitle() {
        // *** Test puro: sin Spring, sin IO. Prueba findAll filtrando por título instanciando el servicio manualmente.
        BookServiceImpl bookService = new BookServiceImpl(bookRepository);
        Book b1 = entity(UUID.randomUUID(), "Cien años de soledad", 1L, "Novela", "978-0-06-088328-7");
        when(bookRepository.findByTitleContainingIgnoreCase("cien")).thenReturn(List.of(b1));

        List<BookResponse> result = bookService.findAll("cien", null);

        assertEquals(1, result.size());
        assertEquals("Cien años de soledad", result.get(0).title());
    }

    @Test
    void findAll_shouldFilterByCategory() {
        // *** Test puro: sin Spring, sin IO. Prueba findAll filtrando por categoría instanciando el servicio manualmente.
        BookServiceImpl bookService = new BookServiceImpl(bookRepository);
        Book b1 = entity(UUID.randomUUID(), "Cien años de soledad", 1L, "Novela", "978-0-06-088328-7");
        when(bookRepository.findByCategoryContainingIgnoreCase("Novela")).thenReturn(List.of(b1));

        List<BookResponse> result = bookService.findAll(null, "Novela");

        assertEquals(1, result.size());
        assertEquals("Novela", result.get(0).category());
    }

    @Test
    void create_shouldSaveAndReturnDto() {
        // *** Test puro: sin Spring, sin IO. Prueba la creación de un libro instanciando el servicio manualmente.
        BookServiceImpl bookService = new BookServiceImpl(bookRepository);
        UUID bookId = UUID.randomUUID();
        when(bookRepository.save(any(Book.class))).thenAnswer(invocation -> {
            Book toSave = invocation.getArgument(0);
            toSave.setId(bookId);
            return toSave;
        });

        BookResponse result = bookService.create(new BookRequest(
            "El otoño del patriarca",
            1L,
            "Novela",
            "978-0-063-011418-7"
        ));

        assertEquals(bookId, result.id());
        assertEquals("El otoño del patriarca", result.title());

        ArgumentCaptor<Book> captor = ArgumentCaptor.forClass(Book.class);
        verify(bookRepository).save(captor.capture());
        assertEquals("El otoño del patriarca", captor.getValue().getTitle());
    }

    @Test
    void update_shouldUpdateAndReturnDto() {
        // *** Test puro: sin Spring, sin IO. Prueba actualizar un libro existente instanciando el servicio manualmente.
        BookServiceImpl bookService = new BookServiceImpl(bookRepository);
        UUID bookId = UUID.randomUUID();
        Book entity = entity(bookId, "El otoño del patriarca", 1L, "Novela", "978-0-063-011418-7");
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(entity));
        when(bookRepository.save(any(Book.class))).thenAnswer(invocation -> invocation.getArgument(0));

        BookResponse result = bookService.update(bookId, new BookRequest(
            "El otoño del patriarca Updated",
            1L,
            "Novela",
            "978-0-063-011418-7"
        ));

        assertEquals(bookId, result.id());
        assertEquals("El otoño del patriarca Updated", result.title());

        verify(bookRepository).save(entity);
    }

    @Test
    void update_shouldThrowNotFoundWhenMissing() {
        // *** Test puro: sin Spring, sin IO. Prueba actualizar un libro inexistente lanzando excepción.
        BookServiceImpl bookService = new BookServiceImpl(bookRepository);
        UUID bookId = UUID.randomUUID();
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(
            BookNotFoundException.class,
            () -> bookService.update(bookId, new BookRequest("Title", 1L, "Cat", "123"))
        );
    }

    @Test
    void delete_shouldDeleteWhenExists() {
        // *** Test puro: sin Spring, sin IO. Prueba la eliminación exitosa instanciando el servicio manualmente.
        BookServiceImpl bookService = new BookServiceImpl(bookRepository);
        UUID bookId = UUID.randomUUID();
        Book entity = entity(bookId, "1984", 2L, "Distopía", "978-0-451-52493-5");
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(entity));

        bookService.delete(bookId);

        verify(bookRepository).delete(entity);
    }

    @Test
    void delete_shouldThrowNotFoundWhenMissing() {
        // *** Test puro: sin Spring, sin IO. Prueba eliminar un libro inexistente lanzando excepción.
        BookServiceImpl bookService = new BookServiceImpl(bookRepository);
        UUID bookId = UUID.randomUUID();
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(
            BookNotFoundException.class,
            () -> bookService.delete(bookId)
        );
    }

    private Book entity(UUID id, String title, Long authorId, String category, String isbn) {
        return Book.builder()
            .id(id)
            .title(title)
            .authorId(authorId)
            .category(category)
            .isbn(isbn)
            .build();
    }
}
