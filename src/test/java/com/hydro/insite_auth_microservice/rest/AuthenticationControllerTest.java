package com.hydro.insite_auth_microservice.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import com.hydro.factory.abstracts.BaseControllerTest;
import com.hydro.factory.annotations.HydroRestTest;
import com.hydro.insite_auth_microservice.client.domain.AuthToken;
import com.hydro.insite_auth_microservice.client.domain.request.AuthenticationRequest;
import com.hydro.insite_auth_microservice.service.AuthenticationService;
import com.hydro.insite_common_microservice.annotations.interfaces.ControllerJwt;

@HydroRestTest
public class AuthenticationControllerTest extends BaseControllerTest {

    @MockBean
    private AuthenticationService service;

    @Test
    public void testAuthenticate() throws Exception {
        when(service.authenticate(any(AuthenticationRequest.class))).thenReturn(new AuthToken());
        AuthenticationRequest request = new AuthenticationRequest("test@mail.com", "testPassword");
        check(post("/authenticate", request, AuthToken.class), serializedNonNull());
    }

    @Test
    @ControllerJwt
    public void testReAuthenticate() throws Exception {
        when(service.reauthenticate()).thenReturn(new AuthToken());
        check(post("/reauthenticate", AuthToken.class), serializedNonNull());
    }

    @Test
    public void testReAuthenticateNoToken() {
        check(post("/reauthenticate"), error(HttpStatus.UNAUTHORIZED, "Missing JWT Token."));
    }
}
