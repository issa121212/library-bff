package com.docente.msauth.security;

import java.util.Base64;
import java.util.UUID;

import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;


@Component
public class RsaKeyProvider {

    private final RSAPrivateKey privateKey;
    private final RSAPublicKey  publicKey;
    private final RSAKey        rsaJwk;

    public RsaKeyProvider(
            @Value("${rsa.private-key-path}") String privatePath,
            @Value("${rsa.public-key-path}")  String publicPath) throws Exception {
        this.privateKey = loadPrivateKey(privatePath);
        this.publicKey  = loadPublicKey(publicPath);
        this.rsaJwk = new RSAKey.Builder(this.publicKey)
            .privateKey(this.privateKey)
            .keyID(UUID.randomUUID().toString())
            .build();
    }

    public RSAPrivateKey getPrivateKey() { return privateKey; }
    public RSAPublicKey  getPublicKey()  { return publicKey; }
    public RSAKey        getRsaJwk()     { return rsaJwk; }

    private RSAPrivateKey loadPrivateKey(String path) throws Exception {
        String pem = new String(Files.readAllBytes(Paths.get(path)))
            .replace("-----BEGIN PRIVATE KEY-----", "")
            .replace("-----END PRIVATE KEY-----", "")
            .replaceAll("\\s", "");
        byte[] decoded = Base64.getDecoder().decode(pem);
        return (RSAPrivateKey) KeyFactory.getInstance("RSA")
            .generatePrivate(new PKCS8EncodedKeySpec(decoded));
    }

    private RSAPublicKey loadPublicKey(String path) throws Exception {
        String pem = new String(Files.readAllBytes(Paths.get(path)))
            .replace("-----BEGIN PUBLIC KEY-----", "")
            .replace("-----END PUBLIC KEY-----", "")
            .replaceAll("\\s", "");
        byte[] decoded = Base64.getDecoder().decode(pem);
        return (RSAPublicKey) KeyFactory.getInstance("RSA")
            .generatePublic(new X509EncodedKeySpec(decoded));
    }
}
