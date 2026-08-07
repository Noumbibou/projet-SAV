package com.emsi.sav.serviceagents.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    private final SecretKey cle = Keys.hmacShaKeyFor(
            "cette-cle-secrete-doit-faire-au-moins-32-caracteres-de-long".getBytes());

    private static final long DUREE_VALIDITE_MS = 1000 * 60 * 60 * 10; // 10 heures

    public String genererToken(String email, String role) {
        return Jwts.builder()
                .subject(email)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + DUREE_VALIDITE_MS))
                .signWith(cle)
                .compact();
    }

    public String extraireEmail(String token) {
        return extraireClaim(token, Claims::getSubject);
    }

    public boolean estValide(String token, String email) {
        return extraireEmail(token).equals(email) && !estExpire(token);
    }

    private boolean estExpire(String token) {
        return extraireClaim(token, Claims::getExpiration).before(new Date());
    }

    private <T> T extraireClaim(String token, Function<Claims, T> resolver) {
        Claims claims = Jwts.parser().verifyWith(cle).build().parseSignedClaims(token).getPayload();
        return resolver.apply(claims);
    }
}