package com.docente.msauth.service;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.docente.msauth.dto.AuthResponse;
import com.docente.msauth.dto.LoginRequest;
import com.docente.msauth.dto.RegisterRequest;
import com.docente.msauth.entity.User;
import com.docente.msauth.repository.UserRepository;
import com.docente.msauth.security.JwtService;

// Integra Mockito con JUnit 5.
// Gracias a esta anotacion, los campos con @Mock se crean automaticamente antes de cada test.
// Sin esto, tendriamos que inicializar mocks manualmente.
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Test
    void register_shouldRegisterAndReturnToken_whenUsernameDoesNotExist() throws Exception {
        // *** Este test prueba el registro correcto instanciando manualmente el servicio
        AuthService authService = new AuthService(userRepository, passwordEncoder, jwtService);
        RegisterRequest request = new RegisterRequest("newuser", "plain_password");
        when(userRepository.existsByUsername("newuser")).thenReturn(false);
        when(passwordEncoder.encode("plain_password")).thenReturn("hashed_password");
        when(jwtService.generateToken("newuser")).thenReturn("mocked_token");

        AuthResponse response = authService.register(request);

        assertNotNull(response);
        assertEquals("mocked_token", response.accessToken());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void register_shouldThrowException_whenUsernameAlreadyExists() {
        // *** Este test prueba el lanzamiento de excepción si el usuario ya existe instanciando manualmente el servicio
        AuthService authService = new AuthService(userRepository, passwordEncoder, jwtService);
        RegisterRequest request = new RegisterRequest("existinguser", "pass");
        when(userRepository.existsByUsername("existinguser")).thenReturn(true);

        assertThrows(RuntimeException.class, () -> authService.register(request));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void login_shouldReturnToken_whenCredentialsAreValid() throws Exception {
        // *** Este test prueba el login exitoso instanciando manualmente el servicio
        AuthService authService = new AuthService(userRepository, passwordEncoder, jwtService);
        LoginRequest request = new LoginRequest("user1", "pass1");
        User user = new User(1L, "user1", "hashed_pass1");
        
        when(userRepository.findByUsername("user1")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("pass1", "hashed_pass1")).thenReturn(true);
        when(jwtService.generateToken("user1")).thenReturn("mocked_token");

        AuthResponse response = authService.login(request);

        assertNotNull(response);
        assertEquals("mocked_token", response.accessToken());
    }

    @Test
    void login_shouldThrowException_whenUserNotFound() {
        // *** Este test prueba el login fallido por usuario no encontrado instanciando manualmente el servicio
        AuthService authService = new AuthService(userRepository, passwordEncoder, jwtService);
        LoginRequest request = new LoginRequest("unknown", "pass");
        when(userRepository.findByUsername("unknown")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> authService.login(request));
    }

    @Test
    void login_shouldThrowException_whenPasswordIncorrect() {
        // *** Este test prueba el login fallido por contraseña incorrecta instanciando manualmente el servicio
        AuthService authService = new AuthService(userRepository, passwordEncoder, jwtService);
        LoginRequest request = new LoginRequest("user1", "wrong_pass");
        User user = new User(1L, "user1", "hashed_pass1");
        
        when(userRepository.findByUsername("user1")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrong_pass", "hashed_pass1")).thenReturn(false);

        assertThrows(RuntimeException.class, () -> authService.login(request));
    }

    @Test
    void register_shouldThrowRuntimeException_whenTokenGenerationFails() throws Exception {
        // *** Este test prueba el control de errores en generación de token al registrar un usuario
        AuthService authService = new AuthService(userRepository, passwordEncoder, jwtService);
        RegisterRequest request = new RegisterRequest("newuser", "plain_password");
        when(userRepository.existsByUsername("newuser")).thenReturn(false);
        when(passwordEncoder.encode("plain_password")).thenReturn("hashed_password");
        when(jwtService.generateToken("newuser")).thenThrow(new RuntimeException("token error"));

        assertThrows(RuntimeException.class, () -> authService.register(request));
    }

    @Test
    void login_shouldThrowRuntimeException_whenTokenGenerationFails() throws Exception {
        // *** Este test prueba el control de errores en generación de token al iniciar sesión
        AuthService authService = new AuthService(userRepository, passwordEncoder, jwtService);
        LoginRequest request = new LoginRequest("user1", "pass1");
        User user = new User(1L, "user1", "hashed_pass1");
        
        when(userRepository.findByUsername("user1")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("pass1", "hashed_pass1")).thenReturn(true);
        when(jwtService.generateToken("user1")).thenThrow(new RuntimeException("token error"));

        assertThrows(RuntimeException.class, () -> authService.login(request));
    }
}
