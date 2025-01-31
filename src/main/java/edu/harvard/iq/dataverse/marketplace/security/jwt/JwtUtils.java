package edu.harvard.iq.dataverse.marketplace.security.jwt;

import java.security.Key;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import edu.harvard.iq.dataverse.marketplace.security.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/**
 * This class is used to generate and validate JWT tokens.
 * 
 * The duration and the secret key for the token are defined 
 * in the application.properties file.
 * 
 */
@Component
public class JwtUtils {

    Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${dataverse.mkt.jwtSecret}")
    private String jwtSecret;

    @Value("${dataverse.mkt.jwtExpirationMs}")
    private int jwtExpirationMs;

    /**
     * This method is used to generate JWT token.
     * 
     * @param authentication
     * @return
     */
    public String generateJwtToken(Authentication authentication) {

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        return Jwts.builder()
            .subject((userPrincipal.getUsername()))
            .issuedAt(new Date())
            .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
            //.signWith(key(), Jwts.SIG.RS256)
            .signWith(key(), SignatureAlgorithm.HS256)
            //.signWith(key(), Jwts.SIG.RS256)
            .compact();
    }

    /**
     * This method is used to get key for JWT token.
     * @return
     */
    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }
    
    /**
     * This method is used to get username from JWT token.
     * 
     * @param token
     * @return
     */
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
            .setSigningKey(key())
            .build()
            .parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * This method is used to validate JWT token.
     * @param authToken
     * @return
     */
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(key()).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

}
