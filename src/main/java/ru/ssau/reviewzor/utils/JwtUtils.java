package ru.ssau.reviewzor.utils;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ru.ssau.reviewzor.service.UserDetailsImpl;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Component
public class JwtUtils {
    @Value("${app.jwtSecret}")
    private String jwtSecret;
    @Value("${app.jwtExpirationMs}")
    private long jwtExpirationMs;

    private SecretKey key;
    private JwtParser parser;

    @PostConstruct
    public void init() {
        key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        parser = Jwts.parserBuilder()
                .setSigningKey(key)
                .build();
    }

    public String generateJwtToken(final Authentication authentication) {
        final UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder().setSubject(userPrincipal.getUsername()).setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpirationMs))
                .signWith(key, SignatureAlgorithm.HS512).compact();
    }

    public boolean validateJwtToken(final String jwt) {
        try {
            parser.parseClaimsJws(jwt);
            return true;
        } catch (IllegalArgumentException | MalformedJwtException ex) {
            log.error(ex.getMessage(), ex);
        }
        return false;
    }

    public String getUsernameFromJwt(final String jwt) {
        return parser.parseClaimsJws(jwt).getBody().getSubject();
    }
}
