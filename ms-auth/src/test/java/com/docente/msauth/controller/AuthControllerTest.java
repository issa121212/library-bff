package com.docente.msauth.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.docente.msauth.dto.AuthResponse;
import com.docente.msauth.dto.LoginRequest;
import com.docente.msauth.dto.RegisterRequest;
import com.docente.msauth.service.AuthService;

// Integra Mockito con JUnit 5.
// Gracias a esta anotacion, los campos con @Mock se crean automaticamente antes de cada test.
// Sin esto, tendriamos que inicializar mocks manualmente.
@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthService authService;

    @Test
    void register_shouldReturnCreatedAndBody() {
        // *** Este test prueba la creación de usuario instanciando manualmente el controlador
        AuthController authController = new AuthController(authService);
        RegisterRequest request = new RegisterRequest("user", "pass");
        AuthResponse response = new AuthResponse("token123");
        when(authService.register(request)).thenReturn(response);

        ResponseEntity<AuthResponse> result = authController.register(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals("token123", result.getBody().accessToken());
        verify(authService).register(request);
    }

    @Test
    void login_shouldReturnOkAndBody() {
        // *** Este test prueba el inicio de sesión exitoso instanciando manualmente el controlador
        AuthController authController = new AuthController(authService);
        LoginRequest request = new LoginRequest("user", "pass");
        AuthResponse response = new AuthResponse("token123");
        when(authService.login(request)).thenReturn(response);

        ResponseEntity<AuthResponse> result = authController.login(request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals("token123", result.getBody().accessToken());
        verify(authService).login(request);
    }
}
