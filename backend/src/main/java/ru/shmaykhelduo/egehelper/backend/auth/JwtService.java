package ru.shmaykhelduo.egehelper.backend.auth;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class JwtService {
    @Value("${jwt.key}")
    private String secret;

    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plus(1, ChronoUnit.HOURS)))
                .signWith(getSecretKey())
                .compact();
    }

    public String extractUsername(String token) {
        return getParser()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    private JwtParser getParser() {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build();
    }

    private SecretKey getSecretKey() {
        byte[] bytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(bytes);
    }
}
