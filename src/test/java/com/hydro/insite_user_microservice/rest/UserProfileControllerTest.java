package com.hydro.insite_user_microservice.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hydro.factory.abstracts.BaseControllerTest;
import com.hydro.factory.annotations.HydroRestTest;
import com.hydro.insite_common_microservice.annotations.interfaces.ControllerJwt;
import com.hydro.insite_common_microservice.exceptions.helper.ExceptionError;
import com.hydro.insite_user_microservice.client.domain.User;

@HydroRestTest
@Sql("/scripts/user/userProfileRest/init.sql")
@ControllerJwt
public class UserProfileControllerTest extends BaseControllerTest {

    @Autowired
    private ObjectMapper objMapper;

    @Test
    public void testGetListOfUsers() {
        check(get("/api/user-app/profile", User[].class),
              serialized(HttpStatus.OK, body -> assertEquals(3, body.length, "Length should be 3")));
    }

    @Test
    public void testGetUserById() {
        check(get("/api/user-app/profile/3", User.class), serialized(HttpStatus.OK, body -> {
            assertEquals(3, body.getId(), "User Id");
        }));
    }

    @Test
    public void testGetUserByIdNotFound() {
        check(getError("/api/user-app/profile/100"), serialized(HttpStatus.INTERNAL_SERVER_ERROR, body -> {
            ExceptionError e = objMapper.convertValue(body, ExceptionError.class);
            assertEquals("User not found for id: '100'", e.getMessage(), "Exception Message");
        }));
    }
}
