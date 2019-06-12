package com.besafx.app.jwt;

import com.besafx.app.auditing.PersonAwareUserDetails;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {

    private static final Logger LOG = LoggerFactory.getLogger(JwtProvider.class);

    @Value("${rmgs.app.jwtSecret}")
    private String jwtSecret;

    @Value("${rmgs.app.jwtExpiration}")
    private int jwtExpiration;

    public String generateJwtToken(Authentication authentication) {

        PersonAwareUserDetails userPrincipal = (PersonAwareUserDetails) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpiration * 1000))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            LOG.info("TOKEN: " + authToken);
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            LOG.error("Invalid JWT signature -> Message: {} ", e);
        } catch (MalformedJwtException e) {
            LOG.error("Invalid JWT accessToken -> Message: {}", e);
        } catch (ExpiredJwtException e) {
            LOG.error("Expired JWT accessToken -> Message: {}", e);
        } catch (UnsupportedJwtException e) {
            LOG.error("Unsupported JWT accessToken -> Message: {}", e);
        } catch (IllegalArgumentException e) {
            LOG.error("JWT claims string is empty -> Message: {}", e);
        }

        return false;
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody().getSubject();
    }
}
