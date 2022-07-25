package com.hydro.insite_jwt_microservice.utility;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.hydro.insite_user_microservice.client.domain.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Token util to create and manage jwt tokens.
 * 
 * @author Sam Butler
 * @since July 31, 2021
 */
@Service
public class JwtTokenUtil implements Serializable {

    public long JWT_TOKEN_VALIDITY;

    private String secret;

    public JwtTokenUtil() {
        secret = JwtEnvironment.getSigningKey();
        JWT_TOKEN_VALIDITY = 18000000; // 5 hours
    }

    /**
     * Pulls the expiration date from a given token
     * 
     * @param token - The token being inspected
     * @return A Date object
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * Get Specfic claims from a token based on the passed in resolver
     * 
     * @param <T>            - Object type
     * @param token          - Token to be inspected
     * @param claimsResolver - Claims resolver
     * @return The generic type passed in of the claims
     */
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Pulls all the claims off of a given token
     * 
     * @param token - The token to inspect and pull the claims from
     * @return Claims object is returned
     */
    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    /**
     * Checks if the given token is expired
     * 
     * @param token - Token to pull the expiration date from
     * @return Returns a boolean object of true, false, or null
     */
    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * generate token for reset password as false by default.
     * 
     * @param user - User info to be added to the token
     * @return String of the new JWT token
     * @throws Exception
     */
    public String generateToken(User user) throws Exception {
        return generateToken(user, false);
    }

    /**
     * Sets up the fields to be added to the token. Also takes in a reset param that
     * will say if the token is for a reset password.
     * 
     * @param user  User info to be added to the token
     * @param reset If this is a reset password token.
     * @return String of the new JWT token
     * @throws Exception
     */
    public String generateToken(User user, boolean reset) throws Exception {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("firstName", user.getFirstName());
        claims.put("lastName", user.getLastName());
        claims.put("email", user.getEmail());
        claims.put("webRole", user.getWebRole());
        claims.put("env", JwtEnvironment.getEnvironment());
        claims.put("passwordReset", reset);

        return doGenerateToken(claims);
    }

    /**
     * Generate a token based on the given Claims and subject
     * 
     * @param claims  - The claims/fields to be added to the token
     * @param subject - The main subject to be added to the field
     * @return String of the generated JWT token
     */
    private String doGenerateToken(Map<String, Object> claims) {
        return Jwts.builder().setClaims(claims).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }
}
