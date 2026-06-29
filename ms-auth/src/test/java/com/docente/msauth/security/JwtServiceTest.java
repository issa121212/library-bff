package com.docente.msauth.security;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nimbusds.jose.jwk.RSAKey;

// Integra Mockito con JUnit 5.
// Gracias a esta anotacion, los campos con @Mock se crean automaticamente antes de cada test.
// Sin esto, tendriamos que inicializar mocks manualmente.
@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    @Mock
    private RsaKeyProvider rsaKeyProvider;

    @Test
    void generateToken_shouldReturnSignedJwtString() throws Exception {
        // *** Este test prueba la generación del token JWT firmado por RSA instanciando manualmente el servicio
        JwtService jwtService = new JwtService(rsaKeyProvider);
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

        RSAKey rsaJwk = new RSAKey.Builder(publicKey)
            .keyID(UUID.randomUUID().toString())
            .build();

        when(rsaKeyProvider.getPrivateKey()).thenReturn(privateKey);
        when(rsaKeyProvider.getRsaJwk()).thenReturn(rsaJwk);

        String token = jwtService.generateToken("userTest");

        assertNotNull(token);
    }
}
