package br.com.isabelxis.resume_ats_backend.service.jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Service;

import br.com.isabelxis.resume_ats_backend.entity.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private static final String SECRET =  "minha-chave-super-secreta-com-mais-de-32-bytes-123456";

    //expira em 15 min
    private static final long ACCESS_EXPIRATION  = 1000 * 60 * 60 * 24;

    //expira em 7 dias
    private static final long REFRESH_EXPIRATION = 1000 * 60 * 60 * 24 * 7;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(
            SECRET.getBytes(StandardCharsets.UTF_8)
        );
    }

    //access token
    public String generateAccessToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("type", "access")
                .setIssuedAt(new Date())
                .setExpiration(
                    new Date(System.currentTimeMillis() + ACCESS_EXPIRATION ))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    //refresh token
    public String generateRefreshToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("type", "refresh")
                .setIssuedAt(new Date())
                .setExpiration(
                    new Date(System.currentTimeMillis() + REFRESH_EXPIRATION))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    //Extração


    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();

    } 

    public String extractTokenType(String token){
        return extractAllClaims(token).get("type", String.class);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    //Validação
    public boolean isAccessTokenValid(String token, User user){
        return isTokenValid(token, user,"access");
    }

    public boolean isRefreshTokenValid(String token, User user){
        return isTokenValid(token, user,"refresh");
    }

    public boolean isTokenValid(String token, User user, String expectedType) {
        String email = extractEmail(token);
        String type = extractTokenType(token);

        return email.equals(user.getEmail()) 
                && type.equals(expectedType)
                && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        Date expiration = extractAllClaims(token).getExpiration();
        return expiration.before(new Date());
    }

    
}