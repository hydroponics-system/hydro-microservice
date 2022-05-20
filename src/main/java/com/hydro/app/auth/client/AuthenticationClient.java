package com.hydro.app.auth.client;

import com.hydro.annotations.interfaces.Client;
import com.hydro.app.auth.client.domain.AuthToken;
import com.hydro.app.auth.client.domain.request.AuthenticationRequest;
import com.hydro.app.auth.service.AuthenticationService;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Client method for authentication of a user.
 * 
 * @author Sam Butler
 * @since July 31, 2021
 */
@Client
public class AuthenticationClient {

    @Autowired
    private AuthenticationService service;

    /**
     * Verifies user credentials passed as a JWT Request
     *
     * @param email    - Entered email at login.
     * @param password - Password entered at login.
     * @throws Exception
     */
    public AuthToken authenticate(String email, String password) throws Exception {
        return service.authenticate(new AuthenticationRequest(email, password));
    }
}
