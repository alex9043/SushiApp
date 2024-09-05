package ru.alex9043.sushiapp.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.alex9043.sushiapp.model.user.RefreshToken;
import ru.alex9043.sushiapp.repository.user.RefreshTokenRepository;
import ru.alex9043.sushiapp.repository.user.UserRepository;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtService {
    private static final SecretKey ACCESS_TOKEN_SECRET = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final SecretKey REFRESH_TOKEN_SECRET = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long ACCESS_TOKEN_VALIDITY_SECONDS = 1000 * 60 * 15; // 15 minutes
    private static final long REFRESH_TOKEN_VALIDITY_SECONDS = 1000 * 60 * 60 * 24 * 14; // 14 days
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    public String extractPhoneForAccessToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isAccessTokenValid(String token, UserDetails userDetails) {
        String phone = extractPhoneForAccessToken(token);
        return phone.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public String generateAccessToken(UserDetails userDetails) {
        return generateToken(userDetails, ACCESS_TOKEN_SECRET, ACCESS_TOKEN_VALIDITY_SECONDS);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        String token = generateToken(userDetails, REFRESH_TOKEN_SECRET, REFRESH_TOKEN_VALIDITY_SECONDS);
        saveRefreshToken(token, userDetails);
        return token;
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(ACCESS_TOKEN_SECRET)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private String generateToken(UserDetails userDetails, SecretKey key, long validity) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("roles", userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + validity))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    private void saveRefreshToken(String token, UserDetails userDetails) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(token);
        refreshToken.setExpirationDate(new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALIDITY_SECONDS));
        refreshToken.setUser(userRepository.findByPhone(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found")));
        refreshTokenRepository.save(refreshToken);
    }
}
