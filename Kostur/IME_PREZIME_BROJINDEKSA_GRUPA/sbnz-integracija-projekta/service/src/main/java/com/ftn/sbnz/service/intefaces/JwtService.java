package com.ftn.sbnz.service.intefaces;

import com.ftn.sbnz.model.User;
import com.ftn.sbnz.model.enums.UserType;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

public interface JwtService {
     String generateToken(User userModel);
    String extractUsername(String token);
    Date extractExpiration(String token);
    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);
    Boolean validateToken(String token, UserDetails userDetails);
    UserType extractRole(String token);

    Long extractUserId(String token);
}
