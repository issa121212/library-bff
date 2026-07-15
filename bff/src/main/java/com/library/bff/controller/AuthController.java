package com.library.bff.controller;

import com.library.bff.client.AuthClient;
import com.library.bff.dto.AuthResponse;
import com.library.bff.dto.LoginRequest;
import com.library.bff.dto.RegisterRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthClient authClient;

    /**
     * Endpoint expuesto en el BFF para el registro de nuevos usuarios en el sistema.
     * Canaliza la solicitud hacia el microservicio ms-auth de forma transparente.
     *
     * @param request Datos del nuevo usuario (validado con @Valid)
     * @return AuthResponse conteniendo el token JWT
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        log.info("BFF: Recibida solicitud de registro para usuario: {}", request.username());
        AuthResponse response = authClient.register(request);
        log.info("BFF: Registro exitoso en ms-auth. Retornando token.");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Endpoint expuesto en el BFF para el inicio de sesión (Login) de usuarios.
     * Canaliza la solicitud hacia el microservicio ms-auth de forma transparente.
     *
     * @param request Credenciales del usuario (validado con @Valid)
     * @return AuthResponse conteniendo el token JWT
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        log.info("BFF: Recibida solicitud de login para usuario: {}", request.username());
        AuthResponse response = authClient.login(request);
        log.info("BFF: Login exitoso en ms-auth. Retornando token.");
        return ResponseEntity.ok(response);
    }
}
