package com.hydro.app.auth.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.hydro.app.auth.client.domain.request.AuthenticationRequest;
import com.hydro.app.auth.dao.AuthenticationDAO;
import com.hydro.app.user.client.UserProfileClient;
import com.hydro.app.user.client.domain.User;
import com.hydro.app.user.client.domain.request.UserGetRequest;
import com.hydro.common.exceptions.InvalidCredentialsException;
import com.hydro.factory.annotations.HydroServiceTest;
import com.hydro.jwt.utility.JwtTokenUtil;

@HydroServiceTest
public class AuthenticationServiceTest {

    @Mock
    private AuthenticationDAO authenticationDAO;

    @Mock
    private UserProfileClient userProfileClient;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @InjectMocks
    private AuthenticationService service;

    @Test
    public void testAuthenticateUser() throws Exception {
        User userLoggingIn = new User();
        userLoggingIn.setId(1);

        AuthenticationRequest authRequest = new AuthenticationRequest();
        authRequest.setEmail("fake@mail.com");
        authRequest.setPassword("testPassword");

        when(authenticationDAO.getUserAuthPassword(anyString()))
                .thenReturn("$2a$10$KusdNWjdceySzNAG3EH8a.5HuIOMWH4hl4Ke64Daqaeqivy1y0Rd.");
        when(userProfileClient.getUsers(any(UserGetRequest.class))).thenReturn(Arrays.asList(userLoggingIn));
        when(userProfileClient.updateUserLastLoginToNow(anyInt())).thenReturn(userLoggingIn);

        service.authenticate(authRequest);

        verify(authenticationDAO).getUserAuthPassword(anyString());
        verify(userProfileClient).getUsers(any(UserGetRequest.class));
        verify(userProfileClient).updateUserLastLoginToNow(1);
        verify(jwtTokenUtil).generateToken(userLoggingIn);
    }

    @Test
    public void testAuthenticateUserInvalidCredentials() throws Exception {
        User userLoggingIn = new User();
        userLoggingIn.setId(1);

        AuthenticationRequest authRequest = new AuthenticationRequest();
        authRequest.setEmail("fake@mail.com");
        authRequest.setPassword("WrongPassword!");

        when(authenticationDAO.getUserAuthPassword(anyString()))
                .thenReturn("$2a$10$KusdNWjdceySzNAG3EH8a.5HuIOMWH4hl4Ke64Daqaeqivy1y0Rd.");

        InvalidCredentialsException e = assertThrows(InvalidCredentialsException.class,
                () -> service.authenticate(authRequest));

        assertEquals("Invalid Credentials for user email: 'fake@mail.com'", e.getMessage(), "Exception Message");
        verify(authenticationDAO).getUserAuthPassword(anyString());
        verify(userProfileClient, never()).getUsers(any(UserGetRequest.class));
        verify(userProfileClient, never()).updateUserLastLoginToNow(1);
        verify(jwtTokenUtil, never()).generateToken(userLoggingIn);
    }
}
