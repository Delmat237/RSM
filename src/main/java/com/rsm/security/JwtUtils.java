package com.rsm.security;

import io.jsonwebtoken.*;

import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtils {

    // Clé secrète (doit faire au moins 256 bits pour HS256)
    private final String SECRET_KEY = "supersecretkeysupersecretkeysupersecretkey1234";

    // Durée de validité du token (1 heure ici)
    private final long EXPIRATION_TIME = 1000 * 60 * 60;

    // Générer un token JWT à partir d'un UserDetails
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey())
                .compact();
    }

    // Extraire le nom d'utilisateur (email) du token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extraire une information générique du token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Vérifie que le token est valide
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // Vérifie si le token est expiré
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Extraire toutes les claims à partir du token
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Récupère la clé de signature HMAC
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }
}
