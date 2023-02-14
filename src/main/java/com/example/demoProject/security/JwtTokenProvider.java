package com.example.demoProject.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtTokenProvider {
    @Value("${app-jwt-expiration-milisec}")
    private long jwtExpirationDate;

    private Key key;
    private byte[] keyData;
    private Key secretKeySpec;


    public String generateToken(Authentication authentication) {
        String username = authentication.getName();

        Date currentDate = new Date();

        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);
        this.key = MacProvider.generateKey(SignatureAlgorithm.HS512);
        this.keyData = key.getEncoded();
        this.secretKeySpec = new SecretKeySpec(keyData, SignatureAlgorithm.HS512.getJcaName());

        String token = Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setIssuer("DEMO")
                .setAudience("DEMO")
                .setSubject(username)
                .setIssuedAt(currentDate)
                .setExpiration(expireDate)
                .signWith(secretKeySpec, SignatureAlgorithm.HS512)
                .compact();

        return token;
    }

    public String getUsername(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKeySpec)
                .build()
                .parseClaimsJws(token)
                .getBody();
        String username = claims.getSubject();
        return username;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKeySpec)
                    .build()
                    .parse(token);
            return true;
        } catch (Exception ex) {
            throw new RuntimeException("Invalid JWT token");
        }
    }
}
