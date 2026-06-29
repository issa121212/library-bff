package com.docente.msauth.dto;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

class AuthDtoTest {

    private final Validator validator;

    AuthDtoTest() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            this.validator = factory.getValidator();
        }
    }

    @Test
    void loginRequest_shouldBeValidWhenFieldsArePopulated() {
        LoginRequest request = new LoginRequest("user", "pass");
        assertEquals("user", request.username());
        assertEquals("pass", request.password());

        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
    void loginRequest_shouldHaveViolationsWhenFieldsAreBlank() {
        LoginRequest request = new LoginRequest(" ", "");
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(request);
        assertEquals(2, violations.size());
    }

    @Test
    void registerRequest_shouldBeValidWhenFieldsArePopulated() {
        RegisterRequest request = new RegisterRequest("user", "pass");
        assertEquals("user", request.username());
        assertEquals("pass", request.password());

        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
    void registerRequest_shouldHaveViolationsWhenFieldsAreBlank() {
        RegisterRequest request = new RegisterRequest("", " ");
        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);
        assertEquals(2, violations.size());
    }

    @Test
    void authResponse_shouldStoreAccessToken() {
        AuthResponse response = new AuthResponse("token123");
        assertEquals("token123", response.accessToken());
    }
}
