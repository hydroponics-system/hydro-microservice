package com.hydro.app.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import com.hydro.app.user.client.domain.User;
import com.hydro.app.user.client.domain.enums.WebRole;
import com.hydro.app.user.client.domain.request.UserGetRequest;
import com.hydro.app.user.dao.UserProfileDAO;
import com.hydro.common.exceptions.NotFoundException;
import com.hydro.factory.annotations.HydroServiceTest;
import com.hydro.jwt.utility.JwtHolder;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

@HydroServiceTest
@RunWith(SpringRunner.class)
public class UserProfileServiceTest {

    @Mock
    private JwtHolder jwtHolder;

    @Mock
    private UserProfileDAO userProfileDAO;

    @InjectMocks
    private UserProfileService service;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetUsers() {
        User user1 = userData();
        User user2 = new User();
        user2.setId(2);

        when(userProfileDAO.getUsers(any(UserGetRequest.class))).thenReturn(Arrays.asList(user1, user2));

        List<User> returnedUser = service.getUsers(new UserGetRequest());

        verify(userProfileDAO).getUsers(any(UserGetRequest.class));
        assertEquals(user1.getId(), returnedUser.get(0).getId(), "User 1 id should be 12");
        assertEquals(user2.getId(), returnedUser.get(1).getId(), "User 2 id should be 2");
    }

    @Test
    public void testGetCurrentUser() throws Exception {
        User user = userData();
        when(jwtHolder.getRequiredUserId()).thenReturn(12);
        when(userProfileDAO.getUserById(anyInt())).thenReturn(user);

        User returnedUser = service.getCurrentUser();

        verify(jwtHolder).getRequiredUserId();
        verify(userProfileDAO).getUserById(eq(12));
        assertEquals(user.getId(), returnedUser.getId(), "User id should be 12");
    }

    @Test
    public void testGetUserById() throws Exception {
        User user = userData();
        when(userProfileDAO.getUserById(anyInt())).thenReturn(user);

        User returnedUser = service.getUserById(12);

        verify(userProfileDAO).getUserById(eq(12));
        assertEquals(user.getId(), returnedUser.getId(), "User id should be 12");
    }

    @Test
    public void testGetUserByIdInvalidUserId() throws Exception {
        when(jwtHolder.getRequiredUserId()).thenReturn(100);
        when(userProfileDAO.getUserById(anyInt())).thenThrow(new NotFoundException("User", 100));

        assertThrows(NotFoundException.class, () -> service.getUserById(100));
        verify(userProfileDAO).getUserById(eq(100));
    }

    @Test
    public void testDoesEmailExist() {
        when(userProfileDAO.getUsers(any(UserGetRequest.class))).thenReturn(Arrays.asList(userData()));
        assertTrue(service.doesEmailExist("test@user.com"), "Email should exist");
    }

    @Test
    public void testDoesEmailExistNotResults() {
        when(userProfileDAO.getUsers(any(UserGetRequest.class))).thenReturn(Arrays.asList());
        assertFalse(service.doesEmailExist("test@user.com"), "Email does not exist");
    }

    private User userData() {
        User u = new User();
        u.setId(12);
        u.setFirstName("Test");
        u.setLastName("User");
        u.setEmail("test@user.com");
        u.setWebRole(WebRole.ADMIN);
        u.setLastLoginDate(LocalDateTime.now());
        return u;
    }
}
