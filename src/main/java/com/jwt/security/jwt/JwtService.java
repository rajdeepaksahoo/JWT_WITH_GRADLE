package com.jwt.security.jwt;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.Map;
import java.util.function.Function;

public interface JwtService {
    public String getUserNameFromToken(String token);
    public Date getExpirationFromToken(String token);
    public boolean isTokenExpired(String token);
    public boolean isTokenValid(String token);
    public <T> T getClaims(String token, Function<Claims,T> claimResolver);
    public Claims getAllClaimsFromToken(String token);
    public String generateToken(UserDetails details);
    public String doGenerateToken(String subject, Map<String,Object> claims);
}
