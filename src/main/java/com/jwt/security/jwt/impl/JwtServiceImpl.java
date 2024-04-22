package com.jwt.security.jwt.impl;

import com.jwt.repository.SecurityRepository;
import com.jwt.security.jwt.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
    @Value("${secret.key}")
    private String SECRET;
    @Value("${expiration.hours}")
    private Long HOURS;
    @Value("${expiration.minutes}")
    private Long MINUTES;
    @Value("${expiration.seconds}")
    private Long SECONDS;
    @Value("${expiration.milliseconds}")
    private Long MILLISECONDS;

    private final UserDetailsService userDetailsService;
    @Override
    public String getUserNameFromToken(String token) {
        return getClaims(token,Claims::getSubject);
    }

    @Override
    public Date getExpirationFromToken(String token) {
        return getClaims(token,Claims::getExpiration);
    }

    @Override
    public boolean isTokenExpired(String token) {
        return getExpirationFromToken(token).before(new Date());
    }

    @Override
    public boolean isTokenValid(String token) {
        String userNameFromToken = getUserNameFromToken(token);
        return ! isTokenExpired(token) &&
                userNameFromToken.equals(userDetailsService.loadUserByUsername(userNameFromToken).getUsername());
    }

    @Override
    public <T> T getClaims(String token, Function<Claims, T> claimResolver) {
        return claimResolver.apply(getAllClaimsFromToken(token));
    }

    @Override
    public Claims getAllClaimsFromToken(String token) {
        JwtParser parser = Jwts.parser().verifyWith(getSecret()).build();
        return parser.parseSignedClaims(token).getPayload();
    }

    @Override
    public String generateToken(UserDetails details) {
        Map<String,Object> claims  = new HashMap<>();
        return doGenerateToken(details.getUsername(),claims);
    }

    @Override
    public String doGenerateToken(String subject, Map<String, Object> claims) {
        return Jwts.builder()
                .subject(subject)
                .claims(claims)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+(HOURS*MINUTES*SECONDS*MILLISECONDS)))
                .signWith(getSecret())
                .compact();
    }

    private SecretKey getSecret() {
        return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }
}
