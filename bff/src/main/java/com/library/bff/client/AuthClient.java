package com.library.bff.client;

import com.library.bff.dto.AuthResponse;
import com.library.bff.dto.LoginRequest;
import com.library.bff.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class AuthClient {

    private final RestClient restClient;

    @Value("${services.auth.url}")
    private String authServiceUrl;

    /**
     * Envía la solicitud de login hacia el microservicio de autenticación ms-auth.
     *
     * @param request Credenciales del usuario
     * @return Contenedor del token JWT
     */
    public AuthResponse login(LoginRequest request) {
        return restClient.post()
            .uri(authServiceUrl + "/api/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .body(request)
            .retrieve()
            .body(AuthResponse.class);
    }

    /**
     * Envía la solicitud de registro hacia el microservicio de autenticación ms-auth.
     *
     * @param request Datos del nuevo usuario
     * @return Contenedor del token JWT generado
     */
    public AuthResponse register(RegisterRequest request) {
        return restClient.post()
            .uri(authServiceUrl + "/api/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .body(request)
            .retrieve()
            .body(AuthResponse.class);
    }
}
