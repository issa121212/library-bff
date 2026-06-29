package com.library.author.dto;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;

class AuthorDtoTest {

    private final Validator validator;

    AuthorDtoTest() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            this.validator = factory.getValidator();
        }
    }

    @Test
    void authorRequest_shouldBeValidWhenFieldsArePopulated() {
        AuthorRequest request = new AuthorRequest("George Orwell", "Británica", 1903);
        assertEquals("George Orwell", request.name());
        assertEquals("Británica", request.nationality());
        assertEquals(1903, request.birthYear());

        Set<ConstraintViolation<AuthorRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
    void authorRequest_shouldHaveViolationsWhenFieldsAreBlankOrNull() {
        AuthorRequest request = new AuthorRequest("", " ", null);
        Set<ConstraintViolation<AuthorRequest>> violations = validator.validate(request);
        assertEquals(3, violations.size());
    }

    @Test
    void authorResponse_shouldStoreFields() {
        AuthorResponse response = new AuthorResponse(1L, "Gabriel García Márquez", "Colombiana", 1927);
        assertEquals(1L, response.id());
        assertEquals("Gabriel García Márquez", response.name());
        assertEquals("Colombiana", response.nationality());
        assertEquals(1927, response.birthYear());
    }
}
