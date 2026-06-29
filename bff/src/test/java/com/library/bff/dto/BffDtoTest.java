package com.library.bff.dto;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

class BffDtoTest {

    private final Validator validator;

    BffDtoTest() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            this.validator = factory.getValidator();
        }
    }

    @Test
    void authorRequest_shouldBeValidWhenFieldsArePopulated() {
        // *** Este test prueba la validación correcta de un AuthorRequest con todos sus campos válidos
        AuthorRequest request = new AuthorRequest("Gabriel", "Colombiana", 1927);
        assertEquals("Gabriel", request.name());
        assertEquals("Colombiana", request.nationality());
        assertEquals(1927, request.birthYear());

        Set<ConstraintViolation<AuthorRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
    void authorRequest_shouldHaveViolationsWhenFieldsAreBlankOrNull() {
        // *** Este test prueba las violaciones de validación (NotBlank, NotNull) al pasar campos vacíos o nulos en AuthorRequest
        AuthorRequest request = new AuthorRequest("", " ", null);
        Set<ConstraintViolation<AuthorRequest>> violations = validator.validate(request);
        assertEquals(3, violations.size());
    }

    @Test
    void authorResponse_shouldStoreFields() {
        // *** Este test prueba el correcto almacenamiento y acceso a los campos del record AuthorResponse
        AuthorResponse response = new AuthorResponse(1L, "Gabriel", "Colombiana", 1927);
        assertEquals(1L, response.id());
        assertEquals("Gabriel", response.name());
    }

    @Test
    void bookRequest_shouldBeValidWhenFieldsArePopulated() {
        // *** Este test prueba que un BookRequest completamente lleno pase las restricciones de validación
        BookRequest request = new BookRequest("Title", 1L, "Novela", "123-456");
        assertEquals("Title", request.title());
        assertEquals("Novela", request.category());
        assertEquals(1L, request.authorId());
        assertEquals("123-456", request.isbn());

        Set<ConstraintViolation<BookRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
    void bookRequest_shouldHaveViolationsWhenFieldsAreBlankOrNull() {
        // *** Este test prueba las violaciones de restricciones en BookRequest (campos en blanco, nulos o vacíos)
        BookRequest request = new BookRequest(" ", null, "", " ");
        Set<ConstraintViolation<BookRequest>> violations = validator.validate(request);
        assertEquals(4, violations.size());
    }

    @Test
    void bookResponse_shouldStoreFields() {
        // *** Este test prueba el almacenamiento de valores correctos en el record BookResponse
        UUID id = UUID.randomUUID();
        BookResponse response = new BookResponse(id, "Title", 1L, "Novela", "123-456");
        assertEquals(id, response.id());
        assertEquals("Title", response.title());
        assertEquals("Novela", response.category());
        assertEquals(1L, response.authorId());
        assertEquals("123-456", response.isbn());
    }

    @Test
    void bookWithAuthorResponse_shouldStoreFields() {
        // *** Este test prueba el almacenamiento e integridad de la composición en BookWithAuthorResponse
        UUID id = UUID.randomUUID();
        AuthorResponse author = new AuthorResponse(1L, "Gabriel", "Colombiana", 1927);
        BookWithAuthorResponse response = new BookWithAuthorResponse(id, "Title", "Novela", "123-456", author);
        assertEquals(id, response.id());
        assertEquals("Title", response.title());
        assertEquals("123-456", response.isbn());
        assertEquals(author, response.author());
    }
}
