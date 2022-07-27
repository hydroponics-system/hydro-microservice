package com.hydro.insite_user_microservice.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import com.hydro.factory.abstracts.BaseControllerTest;
import com.hydro.factory.annotations.HydroRestTest;
import com.hydro.insite_common_microservice.annotations.interfaces.ControllerJwt;
import com.hydro.insite_user_microservice.client.domain.User;
import com.hydro.insite_user_microservice.client.domain.enums.WebRole;

@HydroRestTest
@Sql("/scripts/user/userProfileRest/init.sql")
@ControllerJwt
public class UserProfileControllerTest extends BaseControllerTest {

    @Test
    public void testGetListOfUsers() {
        check(get("/api/user-app/profile", User[].class), serializedList(HttpStatus.OK, 3));
    }

    @Test
    public void testGetUserById() {
        check(get("/api/user-app/profile/3", User.class), serialized(HttpStatus.OK, body -> {
            assertEquals(3, body.getId(), "User Id");
            assertEquals("Fake", body.getFirstName(), "First Name");
            assertEquals("User", body.getLastName(), "Last Name");
            assertEquals("Fake123@mail.com", body.getEmail(), "Email");
            assertEquals(WebRole.DEVELOPER, body.getWebRole(), "Webrole");
        }));
    }

    @Test
    public void testGetUserByIdNotFound() {
        check(get("/api/user-app/profile/100"), error(HttpStatus.BAD_REQUEST, "User not found for id: '100'"));
    }
}
