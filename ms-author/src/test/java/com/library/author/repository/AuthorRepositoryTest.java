package com.library.author.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.library.author.model.Author;

@SpringBootTest
@ActiveProfiles("test")
class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    void findById_shouldFindSeededAuthor() {
        var result = authorRepository.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("Gabriel García Márquez", result.get().getName());
    }

    @Test
    void save_shouldPersistNewAuthor() {
        Author author = Author.builder()
            .name("Pablo Neruda")
            .nationality("Chilena")
            .birthYear(1904)
            .build();

        Author saved = authorRepository.save(author);

        assertTrue(saved.getId() > 0);
        assertEquals("Pablo Neruda", saved.getName());
    }
}
