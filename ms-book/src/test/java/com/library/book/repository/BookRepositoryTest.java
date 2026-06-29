package com.library.book.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.library.book.model.Book;

@SpringBootTest
@ActiveProfiles("test")
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    void findByTitleContainingIgnoreCase_shouldFindBook() {
        var result = bookRepository.findByTitleContainingIgnoreCase("cIeN");

        assertEquals(1, result.size());
        assertEquals("Cien años de soledad", result.get(0).getTitle());
    }

    @Test
    void findByCategoryContainingIgnoreCase_shouldFindBooks() {
        var result = bookRepository.findByCategoryContainingIgnoreCase("nOvElA");

        assertTrue(result.size() >= 1);
    }

    @Test
    void save_shouldPersistNewBook() {
        Book book = Book.builder()
            .title("El otoño del patriarca")
            .authorId(1L)
            .category("Novela")
            .isbn("978-0-063-011418-7")
            .build();

        Book saved = bookRepository.save(book);

        assertNotNull(saved.getId());
        assertEquals("El otoño del patriarca", saved.getTitle());
    }
}
