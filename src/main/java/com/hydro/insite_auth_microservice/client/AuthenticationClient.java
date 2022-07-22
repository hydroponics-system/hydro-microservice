package com.hydro.insite_auth_microservice.client;

import org.springframework.beans.factory.annotation.Autowired;

import com.hydro.insite_auth_microservice.client.domain.AuthToken;
import com.hydro.insite_auth_microservice.client.domain.request.AuthenticationRequest;
import com.hydro.insite_auth_microservice.service.AuthenticationService;
import com.hydro.insite_common_microservice.annotations.interfaces.Client;

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
