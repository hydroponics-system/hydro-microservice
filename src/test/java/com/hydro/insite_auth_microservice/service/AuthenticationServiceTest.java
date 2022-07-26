package com.hydro.insite_auth_microservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

import com.hydro.factory.annotations.HydroServiceTest;
import com.hydro.insite_auth_microservice.client.domain.AuthToken;
import com.hydro.insite_auth_microservice.client.domain.request.AuthenticationRequest;
import com.hydro.insite_auth_microservice.dao.AuthenticationDAO;
import com.hydro.insite_common_microservice.exceptions.InvalidCredentialsException;
import com.hydro.insite_common_microservice.exceptions.NotFoundException;
import com.hydro.insite_jwt_microservice.utility.JwtHolder;
import com.hydro.insite_jwt_microservice.utility.JwtTokenUtil;
import com.hydro.insite_user_microservice.client.UserProfileClient;
import com.hydro.insite_user_microservice.client.domain.User;
import com.hydro.insite_user_microservice.client.domain.request.UserGetRequest;

@HydroServiceTest
public class AuthenticationServiceTest {

    @Mock
    private AuthenticationDAO authenticationDAO;

    @Mock
    private UserProfileClient userProfileClient;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private JwtHolder jwtHolder;

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

        AuthToken authToken = service.authenticate(authRequest);

        verify(authenticationDAO).getUserAuthPassword(anyString());
        verify(userProfileClient).getUsers(any(UserGetRequest.class));
        verify(userProfileClient).updateUserLastLoginToNow(1);
        verify(jwtTokenUtil).generateToken(userLoggingIn);
        assertNotNull(authToken, "Auth Token is valid");
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

    @Test
    public void testReAuthenticateUser() throws Exception {
        User userLoggingIn = new User();
        userLoggingIn.setId(1);

        when(userProfileClient.getUserById(anyInt())).thenReturn(userLoggingIn);
        when(userProfileClient.updateUserLastLoginToNow(anyInt())).thenReturn(userLoggingIn);
        when(jwtHolder.getUserId()).thenReturn(1);

        AuthToken authToken = service.reauthenticate();

        verify(authenticationDAO, never()).getUserAuthPassword(any());
        verify(userProfileClient).getUserById(anyInt());
        verify(userProfileClient).updateUserLastLoginToNow(1);
        verify(jwtTokenUtil).generateToken(userLoggingIn);
        assertNotNull(authToken, "Auth Token is valid");
    }

    @Test
    public void testReAuthenticateUserDoesNotExist() throws Exception {
        User userLoggingIn = new User();
        userLoggingIn.setId(1);

        when(userProfileClient.getUserById(anyInt())).thenThrow(NotFoundException.class);
        when(jwtHolder.getUserId()).thenReturn(1);

        assertThrows(NotFoundException.class, () -> service.reauthenticate());
        verify(authenticationDAO, never()).getUserAuthPassword(any());
        verify(userProfileClient, never()).updateUserLastLoginToNow(anyInt());
        verify(jwtTokenUtil, never()).generateToken(userLoggingIn);
        verify(userProfileClient).getUserById(anyInt());
    }
}
