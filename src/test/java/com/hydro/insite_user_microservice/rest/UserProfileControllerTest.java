package com.hydro.insite_user_microservice.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import com.hydro.factory.abstracts.BaseControllerTest;
import com.hydro.factory.annotations.HydroRestTest;
import com.hydro.insite_common_microservice.annotations.interfaces.ControllerJwt;
import com.hydro.insite_user_microservice.client.domain.User;
import com.hydro.insite_user_microservice.client.domain.request.UserGetRequest;
import com.hydro.insite_user_microservice.service.UserProfileService;

@HydroRestTest
@ControllerJwt
public class UserProfileControllerTest extends BaseControllerTest {

    @MockBean
    private UserProfileService service;

    @Test
    public void testGetListOfUsers() throws Exception {
        when(service.getUsers(any(UserGetRequest.class))).thenReturn(Arrays.asList(new User()));
        check(get("/api/user-app/profile", User[].class), serializedList(HttpStatus.OK));
    }

    @Test
    public void testGetUserById() throws Exception {
        when(service.getUserById(anyInt())).thenReturn(new User());
        check(get("/api/user-app/profile/3", User.class), serializedNonNull(HttpStatus.OK));
    }
}
