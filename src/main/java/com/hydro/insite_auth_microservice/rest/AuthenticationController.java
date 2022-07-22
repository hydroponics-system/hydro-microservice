package com.hydro.insite_auth_microservice.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hydro.insite_auth_microservice.client.domain.AuthToken;
import com.hydro.insite_auth_microservice.client.domain.request.AuthenticationRequest;
import com.hydro.insite_auth_microservice.openapi.TagAuthentication;
import com.hydro.insite_auth_microservice.service.AuthenticationService;
import com.hydro.insite_common_microservice.annotations.interfaces.RestApiController;

/**
 * Generates a JWT after being passed a request
 *
 * @author Sam Butler
 * @since August 1, 2021
 */
@RequestMapping("/authenticate")
@RestApiController
@TagAuthentication
public class AuthenticationController {

    @Autowired
    private AuthenticationService service;

    /**
     * Generates a JWT token from a request
     *
     * @param authenticationRequest A email and password request.
     * @return a new JWT.
     * @throws Exception - if authentication request does not match a user.
     */
    @PostMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthToken> authenticateUser(@RequestBody AuthenticationRequest authenticationRequest)
            throws Exception {
        return ResponseEntity.ok(service.authenticate(authenticationRequest));

    }
}
