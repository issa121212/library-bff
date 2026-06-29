package com.docente.msauth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.docente.msauth.dto.AuthResponse;
import com.docente.msauth.dto.LoginRequest;
import com.docente.msauth.dto.RegisterRequest;
import com.docente.msauth.entity.User;
import com.docente.msauth.repository.UserRepository;
import com.docente.msauth.security.JwtService;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository  userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService      jwtService;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.username()))
            throw new RuntimeException("Username already exists: " + request.username());
        User user = User.builder()
            .username(request.username())
            .password(passwordEncoder.encode(request.password()))
            .build();
        userRepository.save(user);
        try {
            return new AuthResponse(jwtService.generateToken(user.getUsername()));
        } catch (Exception e) {
            throw new RuntimeException("Error generating token", e);
        }
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.username())
            .orElseThrow(() -> new RuntimeException("Invalid credentials"));
        if (!passwordEncoder.matches(request.password(), user.getPassword()))
            throw new RuntimeException("Invalid credentials");
        try {
            return new AuthResponse(jwtService.generateToken(user.getUsername()));
        } catch (Exception e) {
            throw new RuntimeException("Error generating token", e);
        }
    }
}
