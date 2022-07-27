package com.hydro.insite_auth_microservice.rest;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import com.hydro.factory.abstracts.BaseControllerTest;
import com.hydro.factory.annotations.HydroRestTest;
import com.hydro.insite_auth_microservice.client.domain.AuthToken;
import com.hydro.insite_auth_microservice.client.domain.request.AuthenticationRequest;
import com.hydro.insite_common_microservice.annotations.interfaces.ControllerJwt;

@HydroRestTest
@Sql("/scripts/auth/authenticationRest/init.sql")
public class AuthenticationControllerTest extends BaseControllerTest {

    @Test
    public void testAuthenticate() {
        AuthenticationRequest request = new AuthenticationRequest("test@mail.com", "testPassword");
        check(post("/authenticate", request, AuthToken.class), serializedNonNull());
    }

    @Test
    public void testAuthenticateInvalidCredentials() {
        AuthenticationRequest request = new AuthenticationRequest("test@mail.com", "WrongPassword");
        check(post("/authenticate", request, AuthToken.class), httpStatusEquals(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Test
    @ControllerJwt
    public void testReAuthenticate() {
        check(post("/reauthenticate", AuthToken.class), serializedNonNull());
    }

    @Test
    public void testReAuthenticateNoToken() {
        check(post("/reauthenticate", AuthToken.class), httpStatusEquals(HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
