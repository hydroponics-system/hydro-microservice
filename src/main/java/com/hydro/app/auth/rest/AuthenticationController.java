package com.hydro.app.auth.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.Date;

import com.hydro.annotations.interfaces.RestApiController;
import com.hydro.app.auth.client.domain.AuthToken;
import com.hydro.app.auth.service.AuthenticationService;
import com.hydro.app.user.client.domain.User;
import com.hydro.jwt.model.AuthenticationRequest;
import com.hydro.jwt.utility.JwtTokenUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;

/**
 * Generates a JWT after being passed a request
 *
 * @author Sam Butler
 * @since August 1, 2021
 */
@RequestMapping("/authenticate")
@RestApiController
@Api(tags = { "Authentication Controller" }, description = "Endpoints for authentication.")
public class AuthenticationController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AuthenticationService authService;

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
        User user = authService.verifyUser(authenticationRequest.getEmail(), authenticationRequest.getPassword());

        final String token = jwtTokenUtil.generateToken(user);
        return ResponseEntity
                .ok(new AuthToken(token, new Date(), jwtTokenUtil.getExpirationDateFromToken(token), user));

    }
}
