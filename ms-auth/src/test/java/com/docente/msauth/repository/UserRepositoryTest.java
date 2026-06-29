package com.docente.msauth.repository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.docente.msauth.entity.User;

@SpringBootTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private com.docente.msauth.security.RsaKeyProvider rsaKeyProvider;

    @Test
    void save_shouldPersistUser() {
        // *** Este test prueba la persistencia de la entidad User en base de datos H2 mediante JpaRepository
        User user = User.builder()
            .username("testuser")
            .password("secret")
            .build();

        User saved = userRepository.save(user);

        assertTrue(saved.getId() > 0);
        assertEquals("testuser", saved.getUsername());
    }

    @Test
    void findByUsername_shouldReturnUserWhenExists() {
        // *** Este test prueba la consulta personalizada findByUsername cuando el usuario existe en base de datos
        User user = User.builder()
            .username("findme")
            .password("password")
            .build();
        userRepository.save(user);

        Optional<User> found = userRepository.findByUsername("findme");

        assertTrue(found.isPresent());
        assertEquals("findme", found.get().getUsername());
    }

    @Test
    void findByUsername_shouldReturnEmptyWhenNotFound() {
        // *** Este test prueba la respuesta vacía de findByUsername al buscar un nombre de usuario inexistente
        Optional<User> found = userRepository.findByUsername("nonexistent");
        assertTrue(found.isEmpty());
    }

    @Test
    void rsaKeyProvider_shouldProvideKeys() {
        // *** Este test prueba la disponibilidad e inicialización correcta de las llaves de seguridad cargadas en RsaKeyProvider
        org.junit.jupiter.api.Assertions.assertNotNull(rsaKeyProvider.getPrivateKey());
        org.junit.jupiter.api.Assertions.assertNotNull(rsaKeyProvider.getPublicKey());
        org.junit.jupiter.api.Assertions.assertNotNull(rsaKeyProvider.getRsaJwk());
    }
}
