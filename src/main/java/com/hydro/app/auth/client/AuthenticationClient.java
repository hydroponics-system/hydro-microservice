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
     * Authenticates a user for the given email and password.
     *
     * @param email    Entered email at login.
     * @param password Password entered at login.
     * @return {@link AuthToken} with the jwt auth.
     * @throws Exception
     */
    public AuthToken authenticate(String email, String password) throws Exception {
        return service.authenticate(new AuthenticationRequest(email, password));
    }
}
