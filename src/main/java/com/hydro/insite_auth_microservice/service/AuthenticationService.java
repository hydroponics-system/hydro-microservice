package com.hydro.insite_auth_microservice.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Sets;
import com.hydro.insite_auth_microservice.client.domain.AuthToken;
import com.hydro.insite_auth_microservice.client.domain.request.AuthenticationRequest;
import com.hydro.insite_auth_microservice.dao.AuthenticationDAO;
import com.hydro.insite_common_microservice.exceptions.InvalidCredentialsException;
import com.hydro.insite_jwt_microservice.utility.JwtTokenUtil;
import com.hydro.insite_user_microservice.client.UserProfileClient;
import com.hydro.insite_user_microservice.client.domain.User;
import com.hydro.insite_user_microservice.client.domain.request.UserGetRequest;

/**
 * Authorization Service takes a user request and checks the values entered for
 * credentials against known values in the database. If correct credentials are
 * passed, it will grant access to the user requested.
 *
 * @author Sam Butler
 * @since August 2, 2021
 */
@Transactional
@Service
public class AuthenticationService {
    @Autowired
    private AuthenticationDAO dao;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserProfileClient userProfileClient;

    /**
     * Generates a JWT token from a request
     *
     * @param authenticationRequest A email and password request.
     * @return a new JWT.
     * @throws Exception - if authentication request does not match a user.
     */
    public AuthToken authenticate(AuthenticationRequest request) throws Exception {
        User user = verifyUser(request.getEmail(), request.getPassword());

        final String token = jwtTokenUtil.generateToken(user);
        return new AuthToken(token, new Date(), jwtTokenUtil.getExpirationDateFromToken(token), user);
    }

    /**
     * Verifies user credentials.
     *
     * @param email    Entered email at login.
     * @param password Password entered at login.
     * @throws Exception Throw an exception if the credentials do not match.
     */
    private User verifyUser(String email, String password) throws Exception {
        if (BCrypt.checkpw(password, dao.getUserAuthPassword(email))) {
            User authUser = getAuthenticatedUser(email);
            return userProfileClient.updateUserLastLoginToNow(authUser.getId());
        } else {
            throw new InvalidCredentialsException(email);
        }
    }

    /**
     * Get a user based on their email address. Used when a user has sucessfully
     * authenticated.
     * 
     * @param email The email to search for.
     * @return {@link User} object of the authenticated user.
     * @throws Exception
     */
    private User getAuthenticatedUser(String email) throws Exception {
        UserGetRequest request = new UserGetRequest();
        request.setEmail(Sets.newHashSet(email));
        return userProfileClient.getUsers(request).get(0);
    }
}
