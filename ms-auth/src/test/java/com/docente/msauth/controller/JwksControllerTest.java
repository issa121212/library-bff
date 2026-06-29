package com.docente.msauth.controller;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.docente.msauth.security.RsaKeyProvider;
import com.nimbusds.jose.jwk.RSAKey;

// Integra Mockito con JUnit 5.
// Gracias a esta anotacion, los campos con @Mock se crean automaticamente antes de cada test.
// Sin esto, tendriamos que inicializar mocks manualmente.
@ExtendWith(MockitoExtension.class)
class JwksControllerTest {

    @Mock
    private RsaKeyProvider rsaKeyProvider;

    @Test
    void jwks_shouldReturnPublicJwkSetMap() throws Exception {
        // *** Este test prueba la consulta de claves públicas JWKS instanciando manualmente el controlador
        JwksController jwksController = new JwksController(rsaKeyProvider);
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

        RSAKey rsaJwk = new RSAKey.Builder(publicKey)
            .keyID(UUID.randomUUID().toString())
            .build();

        when(rsaKeyProvider.getRsaJwk()).thenReturn(rsaJwk);

        Map<String, Object> result = jwksController.jwks();

        assertNotNull(result);
        assertTrue(result.containsKey("keys"));
        assertFalse(((java.util.List<?>) result.get("keys")).isEmpty());
    }
}
