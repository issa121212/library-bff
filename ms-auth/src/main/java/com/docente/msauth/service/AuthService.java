package com.docente.msauth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.docente.msauth.dto.AuthResponse;
import com.docente.msauth.dto.LoginRequest;
import com.docente.msauth.dto.RegisterRequest;
import com.docente.msauth.entity.User;
import com.docente.msauth.repository.UserRepository;
import com.docente.msauth.security.JwtService;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository  userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService      jwtService;

    /**
     * Registra un nuevo usuario en la base de datos de autenticación.
     * Encripta la contraseña usando BCrypt antes de almacenarla.
     *
     * @param request Payload con nombre de usuario y clave plana
     * @return AuthResponse conteniendo el token JWT firmado con RSA
     */
    public AuthResponse register(RegisterRequest request) {
        log.info("Iniciando registro de nuevo usuario: {}", request.username());
        if (userRepository.existsByUsername(request.username())) {
            log.warn("El nombre de usuario ya existe: {}", request.username());
            throw new RuntimeException("Username already exists: " + request.username());
        }
        User user = User.builder()
            .username(request.username())
            .password(passwordEncoder.encode(request.password()))
            .build();
        userRepository.save(user);
        try {
            String token = jwtService.generateToken(user.getUsername());
            log.info("Token generado con éxito para usuario: {}", request.username());
            return new AuthResponse(token);
        } catch (Exception e) {
            log.error("Fallo al generar token JWT para: {}", request.username(), e);
            throw new RuntimeException("Error generating token", e);
        }
    }

    /**
     * Autentica un usuario verificando sus credenciales contra la base de datos.
     *
     * @param request Payload de login con usuario y clave
     * @return AuthResponse conteniendo el token JWT firmado con RSA
     */
    public AuthResponse login(LoginRequest request) {
        log.info("Procesando login para usuario: {}", request.username());
        User user = userRepository.findByUsername(request.username())
            .orElseThrow(() -> {
                log.warn("Intento de login fallido - usuario no encontrado: {}", request.username());
                return new RuntimeException("Invalid credentials");
            });
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            log.warn("Intento de login fallido - clave incorrecta para: {}", request.username());
            throw new RuntimeException("Invalid credentials");
        }
        try {
            String token = jwtService.generateToken(user.getUsername());
            log.info("Inicio de sesión exitoso. Token generado para: {}", request.username());
            return new AuthResponse(token);
        } catch (Exception e) {
            log.error("Fallo al generar token JWT en login para: {}", request.username(), e);
            throw new RuntimeException("Error generating token", e);
        }
    }
}

