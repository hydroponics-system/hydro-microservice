package com.hydro.insite_user_microservice.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import com.google.common.collect.Sets;
import com.hydro.factory.abstracts.BaseControllerTest;
import com.hydro.factory.annotations.HydroRestTest;
import com.hydro.insite_common_microservice.annotations.interfaces.ControllerJwt;
import com.hydro.insite_user_microservice.client.domain.User;
import com.hydro.insite_user_microservice.client.domain.enums.WebRole;
import com.hydro.insite_user_microservice.client.domain.request.UserGetRequest;
import com.hydro.insite_user_microservice.service.ManageUserProfileService;
import com.hydro.insite_user_microservice.service.UserProfileService;

@HydroRestTest
@ControllerJwt
public class UserProfileControllerTest extends BaseControllerTest {

    private static final String USER_PROFILE_PATH = "/api/user-app/profile";

    @MockBean
    private UserProfileService service;

    @MockBean
    private ManageUserProfileService manageService;

    @Captor
    private ArgumentCaptor<UserGetRequest> getUsersCaptor;

    @Test
    public void testGetListOfUsers() throws Exception {
        when(service.getUsers(any(UserGetRequest.class))).thenReturn(Arrays.asList(new User()));
        check(get(USER_PROFILE_PATH, User[].class), serializedList(HttpStatus.OK));

        verify(service).getUsers(any(UserGetRequest.class));
    }

    @Test
    public void testGetListOfUsersWithRequestParams() throws Exception {
        when(service.getUsers(any(UserGetRequest.class))).thenReturn(Arrays.asList(new User()));
        check(get(USER_PROFILE_PATH + "?firstName=test&id=1,2", User[].class), serializedList(HttpStatus.OK));

        verify(service).getUsers(getUsersCaptor.capture());

        UserGetRequest params = getUsersCaptor.getValue();
        assertEquals(Sets.newHashSet("test"), params.getFirstName(), "First Name");
        assertEquals(Sets.newHashSet(1, 2), params.getId(), "ID");
    }

    @Test
    public void testGetCurrentUser() throws Exception {
        when(service.getCurrentUser()).thenReturn(new User());
        check(get(USER_PROFILE_PATH + "/current-user", User.class), serializedNonNull(HttpStatus.OK));

        verify(service).getCurrentUser();
    }

    @Test
    public void testGetUserById() throws Exception {
        when(service.getUserById(anyInt())).thenReturn(new User());
        check(get(USER_PROFILE_PATH + "/3", User.class), serializedNonNull(HttpStatus.OK));

        verify(service).getUserById(3);
    }

    @Test
    @ControllerJwt(webRole = WebRole.USER)
    public void testGetUserByIdNonAdmin() throws Exception {
        when(service.getUserById(anyInt())).thenReturn(new User());
        check(get(USER_PROFILE_PATH + "/3"), error(HttpStatus.FORBIDDEN, "Insufficient Permissions for role 'USER'"));

        verify(service, never()).getUserById(anyInt());
    }

    @Test
    public void testDoesEmailExist() {
        when(service.doesEmailExist(anyString())).thenReturn(false);
        check(get(USER_PROFILE_PATH + "/check-email?email=test@mail.com", Boolean.class), serializedNonNull());

        verify(service).doesEmailExist("test@mail.com");
    }

    @Test
    public void testCreateUser() throws Exception {
        when(manageService.createUser(any(User.class))).thenReturn(new User());
        check(post(USER_PROFILE_PATH, new User(), User.class), serializedNonNull());

        verify(manageService).createUser(any(User.class));
    }

    @Test
    public void testUpdateUser() throws Exception {
        when(manageService.updateUserProfile(any(User.class))).thenReturn(new User());
        check(put(USER_PROFILE_PATH, new User(), User.class), serializedNonNull());

        verify(manageService).updateUserProfile(any(User.class));
    }

    @Test
    public void testUpdateUserProfileById() throws Exception {
        when(manageService.updateUserProfileById(anyInt(), any(User.class))).thenReturn(new User());
        check(put(USER_PROFILE_PATH + "/12", new User(), User.class), serializedNonNull());

        verify(manageService).updateUserProfileById(eq(12), any(User.class));
    }

    @Test
    @ControllerJwt(webRole = WebRole.USER)
    public void testUpdateUserProfileByIdNonAdmin() throws Exception {
        when(manageService.updateUserProfileById(anyInt(), any(User.class))).thenReturn(new User());
        check(put(USER_PROFILE_PATH + "/12", new User()),
              error(HttpStatus.FORBIDDEN, "Insufficient Permissions for role 'USER'"));

        verify(manageService, never()).updateUserProfileById(anyInt(), any());
    }

    @Test
    public void testDeleteUser() throws Exception {
        check(delete(USER_PROFILE_PATH + "/12"), httpStatusEquals(HttpStatus.OK));

        verify(manageService).deleteUser(12);
    }

    @Test
    @ControllerJwt(webRole = WebRole.USER)
    public void testDeleteUserNonAdmin() throws Exception {
        check(delete(USER_PROFILE_PATH + "/12"),
              error(HttpStatus.FORBIDDEN, "Insufficient Permissions for role 'USER'"));

        verify(manageService, never()).deleteUser(anyInt());
    }
}
