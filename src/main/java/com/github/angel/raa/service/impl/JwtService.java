package com.github.angel.raa.service.impl;

import com.github.angel.raa.persistence.entity.UserEntity;
import com.github.angel.raa.persistence.repository.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.MacAlgorithm;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final TokenRepository tokenRepository;
    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.expiration}")
    private long jwtExpirationInMs;
    @Value("${jwt.refresh-expiration}")
    private long refreshExpirationInMs;

    public String extractUsername(String token) {
        return extraClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extraClaim(token, Claims::getExpiration);
    }

    /**
     * Obtiene un claim específico del token JWT.
     *
     * @param token          El token JWT del que se extraerá el claim.
     * @param claimsResolver El resolvedor de claims que se utilizará para obtener el claim específico.
     * @param <T>            El tipo de dato del claim.
     * @return El valor del claim especificado.
     */
    public <T> T extraClaim(String token, @NotNull Function<Claims, T> claimsResolver) {
        Claims payload = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claimsResolver.apply(payload);
    }

    private String createToken(Map<String, Object> claims, String subject, long expirationTime) {
        SecretKey key = getSigningKey();
        MacAlgorithm algorithm = Jwts.SIG.HS256;
        return Jwts.builder()
                .subject(subject)
                .claims(claims)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(key, algorithm)
                .compact();
    }

    public String generateRefreshToken(UserDetails userDetails) {
        UserEntity user = (UserEntity) userDetails;
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", user.getUsername());
        claims.put("Email", user.getEmail());
        claims.put("Role", user.getRole());
        return createToken(claims, user.getUsername(), refreshExpirationInMs);
    }

    public String generateAccessToken(UserDetails userDetails) {
        UserEntity user = (UserEntity) userDetails;
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", user.getUsername());
        claims.put("Email", user.getEmail());
        claims.put("Role", user.getRole());
        return createToken(claims, user.getUsername(), jwtExpirationInMs);


    }

    private @NotNull SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public Boolean isRefreshTokenValid(String token) {
        return tokenRepository.findByToken(token)
                .map(t -> !t.getExpiration().before(new Date()))
                .orElse(false);
    }

    public Boolean validateToken(String token, @NotNull UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));

    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public void deleteRefreshToken(String refresh) {

    }
}
