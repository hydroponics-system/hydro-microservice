package com.hydro.insite_jwt_microservice.interceptor;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import com.hydro.insite_common_microservice.enums.Environment;
import com.hydro.insite_common_microservice.exceptions.JwtTokenException;
import com.hydro.insite_jwt_microservice.utility.JwtEnvironment;
import com.hydro.insite_jwt_microservice.utility.JwtTokenUtil;

/**
 * JWT token validator for confirming a token on a request header.
 *
 * @author Sam butler
 * @since Dec 5, 2020
 */
@Component
public class JwtTokenValidator {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**
     * Checks to see if the token on the request is valid. If it is not valid then
     * it wil throw an exception, otherwise it wil continue. It will confirm that
     * the token is in the right environment, check that it has the correct fields,
     * that it is not expired, and the token signature is valid.
     *
     * @param request - The request that is being made to the endpint
     * @throws JwtTokenException If the jwt token is not valid.
     * @throws ServletException  If the server request is invalid.
     */
    public boolean validateRequest(HttpServletRequest request) throws JwtTokenException, ServletException {
        if(shouldNotFilter(request)) {
            return true;
        }

        final String tokenHeader = request.getHeader("Authorization");

        if(tokenHeader != null && tokenHeader.startsWith("Bearer: ")) {
            String jwtToken = tokenHeader.substring(7).trim();
            validateToken(jwtToken);
        }
        else {
            if(isWebSocketConnection(request)) {
                return validateSocketSession(request.getQueryString());
            }
            else {
                doesTokenExist(tokenHeader);
            }
        }
        return true; // No errors and not websocket connection.
    }

    /**
     * Boolean method that determines if a request should be filtered or not. It
     * will process the antmatchers and determine the result.
     * 
     * @param request The request to validate
     * @return {@link Boolean} of the filter status.
     */
    private boolean shouldNotFilter(HttpServletRequest request) {
        return excludedMatchers().stream().anyMatch(matcher -> matcher.matches(request));
    }

    /**
     * Validates the called socket session to confirm the passed in token is valid.
     * 
     * @param token The token to authenticate.
     * @return The status of the session validation.
     */
    private boolean validateSocketSession(String token) {
        try {
            validateToken(token);
            return true;
        }
        catch(Exception e) {
            return false;
        }
    }

    /**
     * Will take in a string token, without the bearer tag, and confirm that it is
     * valid. This will validate the websocket connection.
     * 
     * @param token The token to validate.
     * @return boolean confirming if it was a valid token.
     * @throws JwtTokenException
     */
    public void validateToken(String token) throws JwtTokenException {
        isCorrectEnvironment(token);
        hasCorrectFields(token);
        isTokenExpired(token);
    }

    /**
     * Checks to see if it is a websocket subscription.
     * 
     * @param token The token to be validated.
     * @return Boolean of the validation status.
     * @throws JwtTokenException
     */
    public boolean isWebSocketConnection(HttpServletRequest request) throws JwtTokenException {
        return request.getRequestURI().contains("/api/subscription");
    }

    /**
     * Checks to see if a token exists or if the token does not contain the bearer
     * keyword.
     *
     * @param tokenHeader Header to of the token.
     * @throws JwtTokenException Throws exception based on status of token.
     */
    private void doesTokenExist(String tokenHeader) throws JwtTokenException {
        if(tokenHeader != null) {
            throw new JwtTokenException("JWT Token does not begin with 'Bearer:'");
        }
        else {
            throw new JwtTokenException("Missing JWT Token.");
        }
    }

    /**
     * Checks to see if it has the required fields on the token.
     *
     * @param token - Token to confirm field claims on
     * @throws JwtTokenException Throws exception if it can't read the fields or if
     *                           it is an invalid token.
     */
    private void hasCorrectFields(String token) throws JwtTokenException {
        try {
            jwtTokenUtil.getExpirationDateFromToken(token);
            jwtTokenUtil.getAllClaimsFromToken(token);
        }
        catch(Exception e) {
            throw new JwtTokenException("Could not process JWT token. Invalid signature!");
        }
    }

    /**
     * Validates that the environemnt is correct.
     *
     * @param token to be parsed
     * @throws JwtTokenException
     */
    private void isCorrectEnvironment(String token) throws JwtTokenException {
        Environment environment = Environment.valueOf(jwtTokenUtil.getAllClaimsFromToken(token).get("env").toString());

        if(!JwtEnvironment.getEnvironment().equals(environment)) {
            throw new JwtTokenException("JWT token doesn't match accessing environment!");
        }
    }

    /**
     * Checks to see if the users token is expired.
     * 
     * @param token The token to validate.
     */
    private void isTokenExpired(String token) {
        if(jwtTokenUtil.isTokenExpired(token)) {
            throw new JwtTokenException("JWT Token is expired! Please re-authenticate.");
        }
    }

    /**
     * Defined filtered matchers that do not need authentication.
     * 
     * @return List of {@link AntPathRequestMatcher} matchers.
     */
    private List<AntPathRequestMatcher> excludedMatchers() {
        List<AntPathRequestMatcher> matchers = new ArrayList<>();
        matchers.add(new AntPathRequestMatcher("/authenticate", "POST"));
        matchers.add(new AntPathRequestMatcher("/api/user-app/profile/check-email", "GET"));
        matchers.add(new AntPathRequestMatcher("/api/user-app/profile", "POST"));
        matchers.add(new AntPathRequestMatcher("/v3/api-docs/**"));
        matchers.add(new AntPathRequestMatcher("/swagger-ui/**"));
        return matchers;
    }
}
