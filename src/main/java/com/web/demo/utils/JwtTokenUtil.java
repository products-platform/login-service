package com.web.demo.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Component
public class JwtTokenUtil {

    private static final String SECRET =
            "mysecretkeymysecretkeymysecretkey12";

    private static final SecretKey SECRET_KEY =
            Keys.hmacShaKeyFor(SECRET.getBytes());

    private static final long EXPIRATION_TIME = 10000 * 60 * 60;

    public String generateToken(String username, String email, String phone, Set<String> roles) {
        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roles)
                .claim("email", email)
                .claim("phone", phone)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    public static String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    public static boolean isTokenValid(String token) {
        try {
            Claims claims = extractAllClaims(token);
            return !isTokenExpired(claims);

        } catch (RuntimeException e) {
            // already mapped meaningful messages in extractAllClaims
            return false;
        }
    }

    private static boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }

    public static Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Token expired", e);

        } catch (UnsupportedJwtException e) {
            throw new RuntimeException("Unsupported token", e);

        } catch (MalformedJwtException e) {
            throw new RuntimeException("Malformed token", e);

        } catch (SignatureException e) {
            throw new RuntimeException("Invalid signature", e);

        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Token is null or empty", e);
        }
    }

    public static String extractPhone(String token) {
        return extractAllClaims(token).get("phone", String.class);
    }

    public static String extractEmail(String token) {
        return extractAllClaims(token).get("email", String.class);
    }

    public static List<String> extractRoles(String token) {
        return extractAllClaims(token).get("roles", List.class);
    }

    public static String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

}