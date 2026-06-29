package com.docente.msauth.security;

import java.util.Date;
import java.util.UUID;
import java.time.Instant;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.*;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final RsaKeyProvider rsaKeyProvider;

    public String generateToken(String username) throws JOSEException {
        JWSSigner signer = new RSASSASigner(rsaKeyProvider.getPrivateKey());
        JWTClaimsSet claims = new JWTClaimsSet.Builder()
            .subject(username)
            .issuer("http://ms-auth:8081")
            .issueTime(Date.from(Instant.now()))
            .expirationTime(Date.from(Instant.now().plusSeconds(3600)))
            .jwtID(UUID.randomUUID().toString())
            .build();
        SignedJWT signedJWT = new SignedJWT(
            new JWSHeader.Builder(JWSAlgorithm.RS256)
                .keyID(rsaKeyProvider.getRsaJwk().getKeyID())
                .build(),
            claims
        );
        signedJWT.sign(signer);
        return signedJWT.serialize();
    }
}
