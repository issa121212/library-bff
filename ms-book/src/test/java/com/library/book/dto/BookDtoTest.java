package com.library.book.dto;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;

class BookDtoTest {

    private final Validator validator;

    BookDtoTest() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            this.validator = factory.getValidator();
        }
    }

    @Test
    void bookRequest_shouldBeValidWhenFieldsArePopulated() {
        BookRequest request = new BookRequest("Cien años de soledad", 1L, "Novela", "978-0307474728");
        assertEquals("Cien años de soledad", request.title());
        assertEquals(1L, request.authorId());
        assertEquals("Novela", request.category());
        assertEquals("978-0307474728", request.isbn());

        Set<ConstraintViolation<BookRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
    void bookRequest_shouldHaveViolationsWhenFieldsAreBlankOrNull() {
        BookRequest request = new BookRequest("", null, " ", "");
        Set<ConstraintViolation<BookRequest>> violations = validator.validate(request);
        assertEquals(4, violations.size());
    }

    @Test
    void bookResponse_shouldStoreFields() {
        UUID id = UUID.randomUUID();
        BookResponse response = new BookResponse(id, "Cien años de soledad", 1L, "Novela", "978-0307474728");
        assertEquals(id, response.id());
        assertEquals("Cien años de soledad", response.title());
        assertEquals(1L, response.authorId());
        assertEquals("Novela", response.category());
        assertEquals("978-0307474728", response.isbn());
    }
}
