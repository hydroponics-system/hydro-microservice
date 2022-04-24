package com.hydro.app.auth.client;

import com.hydro.annotations.interfaces.Client;
import com.hydro.app.auth.client.domain.AuthToken;
import com.hydro.app.auth.rest.AuthenticationController;
import com.hydro.jwt.model.AuthenticationRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

/**
 * Client method for authentication of a user.
 * 
 * @author Sam Butler
 * @since July 31, 2021
 */
@Client
public class AuthenticationClient {

    @Autowired
    private AuthenticationController controller;

    /**
     * Verifies user credentials passed as a JWTRequest
     *
     * @param email    - Entered email at login.
     * @param password - Password entered at login.
     * @throws Exception
     */
    public ResponseEntity<AuthToken> authenticateUser(String email, String password) throws Exception {
        return controller.authenticateUser(new AuthenticationRequest(email, password));
    }
}
