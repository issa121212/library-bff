package com.docente.msauth.controller;

import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.docente.msauth.security.RsaKeyProvider;
import com.nimbusds.jose.jwk.JWKSet;


@RestController
@RequiredArgsConstructor
public class JwksController {

    private final RsaKeyProvider rsaKeyProvider;

    @GetMapping(value = "/.well-known/jwks.json", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> jwks() {
        return new JWKSet(rsaKeyProvider.getRsaJwk().toPublicJWK()).toJSONObject();
    }
}
