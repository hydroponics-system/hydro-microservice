package com.hydro.insite_jwt_microservice.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hydro.insite_common_microservice.enums.Environment;
import com.hydro.insite_common_microservice.exceptions.BaseException;
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
     * @throws IOException If the jwt token is not valid.
     */
    public boolean validateRequest(HttpServletRequest request) throws IOException {
        final String tokenHeader = request.getHeader("Authorization");

        if (tokenHeader != null && tokenHeader.startsWith("Bearer: ")) {
            String jwtToken = tokenHeader.substring(7).trim();

            validateToken(jwtToken);
            isReauthenticating(request.getRequestURI(), jwtToken);

        } else {
            if (isWebSocketConnection(request)) {
                return validateSocketSession(request.getQueryString());
            } else {
                doesTokenExist(tokenHeader);
            }
        }
        return true; // No errors and not websocket connection.
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
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Will take in a string token, without the bearer tag, and confirm that it is
     * valid. This will validate the websocket connection.
     * 
     * @param token The token to validate.
     * @return boolean confirming if it was a valid token.
     * @throws IOException
     */
    public void validateToken(String token) throws IOException {
        isCorrectEnvironment(token);
        hasCorrectFields(token);
    }

    /**
     * Checks to see if it is a websocket subscription.
     * 
     * @param token The token to be validated.
     * @return Boolean of the validation status.
     * @throws IOException
     */
    public boolean isWebSocketConnection(HttpServletRequest request) throws IOException {
        return request.getRequestURI().contains("/api/websocket");
    }

    /**
     * This will check if the endpoint that was called was the /reauthenticate. If
     * it was then we don't care if the token is expired and it will return.
     * Otherwise check if the token is expired.
     *
     * @param endpoint The endpoint that was called.
     * @param token    The token to confirm if it is expired if need be.
     * @throws BaseException Throws exception if the token is expired.
     */
    private void isReauthenticating(String endpoint, String token) throws BaseException {
        if (!endpoint.equals("/reauthenticate")) {
            isTokenExpired(token);
        }
    }

    /**
     * Checks to see if the token that the request pulled is expired. If it is then
     * it will throw an exception.
     *
     * @param token The token to confirm if it is expired or not.
     * @throws BaseException Throws exception if the token is expired.
     */
    private void isTokenExpired(String token) throws BaseException {
        if (jwtTokenUtil.isTokenExpired(token)) {
            throw new BaseException("JWT Token is Expired. Please re-authenticate.");
        }
    }

    /**
     * Checks to see if a token exists or if the token does not contain the bearer
     * keyword.
     *
     * @param tokenHeader Header to of the token.
     * @throws BaseException Throws exception based on status of token.
     */
    private void doesTokenExist(String tokenHeader) throws BaseException {
        if (tokenHeader != null) {
            throw new BaseException("JWT Token does not begin with 'Bearer:'");
        } else {
            throw new BaseException("Missing JWT Token.");
        }
    }

    /**
     * Checks to see if it has the required fields on the token.
     *
     * @param token - Token to confirm field claims on
     * @throws IOException Throws exception if it can't read the fields or if it is
     *                     an invalid token.
     */
    private void hasCorrectFields(String token) throws IOException {
        try {
            jwtTokenUtil.getExpirationDateFromToken(token);
            jwtTokenUtil.getAllClaimsFromToken(token);
        } catch (Exception e) {
            throw new BaseException("Could not process JWT token. Invalid signature!");
        }
    }

    /**
     * Validates that the environemnt is correct.
     *
     * @param token to be parsed
     * @throws BaseException
     */
    private void isCorrectEnvironment(String token) throws BaseException {
        Environment environment = Environment.valueOf(jwtTokenUtil.getAllClaimsFromToken(token).get("env").toString());

        if (!JwtEnvironment.getEnvironment().equals(environment)) {
            throw new BaseException("JWT token doesn't match accessing environment!");
        }
    }
}
