package ru.Senkova.security.jwt;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.jsonwebtoken.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ru.Senkova.security.UserAppPrinciple;

import java.io.Serializable;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class JwtProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtProvider.class);
    private static final String JWT_SIGN_SECRET = RandomStringUtils.random(32, true, true);

    public String generateJwtToken(Authentication authentication) {
        UserAppPrinciple userPrincipal = (UserAppPrinciple) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(ZonedDateTime.now().plusDays(7).toInstant()))
                .claim("currentUser", toJsonString(userPrincipal))
                .signWith(SignatureAlgorithm.HS512, JWT_SIGN_SECRET)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(JWT_SIGN_SECRET)
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(JWT_SIGN_SECRET).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            LOGGER.debug("Invalid JWT signature -> Message: {} ", e.getMessage());
        } catch (MalformedJwtException e) {
            LOGGER.debug("Invalid JWT token -> Message: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            LOGGER.debug("Expired JWT token -> Message: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            LOGGER.debug("Unsupported JWT token -> Message: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            LOGGER.debug("JWT claims string is empty -> Message: {}", e.getMessage());
        }

        return false;
    }

    private String toJsonString(Serializable object) {
        ObjectWriter writer = new ObjectMapper().writer();
        try {
            return writer.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(String.format("Could not transform object '%s' to JSON: ", object), e);
        }
    }
}
