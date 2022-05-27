package com.hydro.app.user.service;

import java.time.LocalDateTime;

import com.hydro.app.user.client.UserCredentialsClient;
import com.hydro.app.user.client.domain.User;
import com.hydro.app.user.client.domain.enums.WebRole;
import com.hydro.app.user.dao.UserProfileDAO;
import com.hydro.factory.annotations.HydroServiceTest;
import com.hydro.jwt.utility.JwtHolder;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

@HydroServiceTest
@RunWith(SpringRunner.class)
public class ManageUserProfileServiceTest {

    @Mock
    private JwtHolder jwtHolder;

    @Mock
    private UserCredentialsClient userCredentialsClient;

    @Mock
    private UserProfileDAO dao;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateUser() {

    }

    @Test
    public void testUpdateUserProfile() {

    }

    @Test
    public void testUpdateUserProfileByIdValid() {

    }

    @Test
    public void testUpdateUserProfileByIdUserHasInsufficientPermissions() {

    }

    @Test
    public void testUpdateUserLastLoginToNow() {

    }

    @Test
    public void testDeleteUserValid() {

    }

    @Test
    public void testDeleteUserUserHasInsufficientPermissions() {

    }

    /**
     * Gets a default user object.
     * 
     * @return {@link User} object for testing.
     */
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
