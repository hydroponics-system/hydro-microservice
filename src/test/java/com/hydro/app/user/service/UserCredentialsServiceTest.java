package com.hydro.app.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.hydro.app.auth.client.AuthenticationClient;
import com.hydro.app.user.client.UserProfileClient;
import com.hydro.app.user.client.domain.PasswordUpdate;
import com.hydro.app.user.client.domain.User;
import com.hydro.app.user.dao.UserCredentialsDAO;
import com.hydro.common.exceptions.InvalidCredentialsException;
import com.hydro.factory.annotations.HydroServiceTest;
import com.hydro.factory.data.UserFactoryData;
import com.hydro.jwt.utility.JwtHolder;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.context.junit4.SpringRunner;

@HydroServiceTest
@RunWith(SpringRunner.class)
public class UserCredentialsServiceTest {

    @Mock
    private JwtHolder jwtHolder;

    @Mock
    private AuthenticationClient authClient;

    @Mock
    private UserProfileClient userProfileClient;

    @Mock
    private UserCredentialsDAO userCredentialsDAO;

    @Captor
    private ArgumentCaptor<String> insertUserPasswordCaptor;

    @InjectMocks
    private UserCredentialsService service;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testInsertUserPassword() {
        service.insertUserPassword(12, "fakePassword");

        verify(userCredentialsDAO).insertUserPassword(eq(12), insertUserPasswordCaptor.capture());
        assertTrue(BCrypt.checkpw("fakePassword", insertUserPasswordCaptor.getValue()), "Password should be hashed");
    }

    @Test
    public void testUpdateUserPasswordValid() throws Exception {
        PasswordUpdate passUpdate = new PasswordUpdate();
        passUpdate.setCurrentPassword("currentPassword123!");
        passUpdate.setNewPassword("newPassword!");

        when(jwtHolder.getRequiredUserId()).thenReturn(12);
        when(jwtHolder.getRequiredEmail()).thenReturn("password@user.com");
        when(userProfileClient.getCurrentUser()).thenReturn(UserFactoryData.userData());

        User userUpdated = service.updateUserPassword(passUpdate);

        verify(authClient).authenticate("password@user.com", "currentPassword123!");
        verify(userCredentialsDAO).updateUserPassword(eq(12), anyString());
        verify(userProfileClient).getCurrentUser();
        assertEquals(12, userUpdated.getId(), "User updated should be id 12");
    }

    @Test
    public void testUpdateUserPasswordAuthenticationFailed() throws Exception {
        when(authClient.authenticate(any(), any())).thenThrow(InvalidCredentialsException.class);
        when(jwtHolder.getRequiredUserId()).thenReturn(12);
        when(jwtHolder.getRequiredEmail()).thenReturn("test@user.com");

        assertThrows(InvalidCredentialsException.class, () -> service.updateUserPassword(new PasswordUpdate()));
        verify(userCredentialsDAO, never()).updateUserPassword(anyInt(), any());
        verify(userProfileClient, never()).getCurrentUser();
    }

    @Test
    public void testUpdateUserPasswordInvalidPassword() throws Exception {
        PasswordUpdate passUpdate = new PasswordUpdate();
        passUpdate.setCurrentPassword("currentPassword123!");
        passUpdate.setNewPassword("");

        when(jwtHolder.getRequiredUserId()).thenReturn(12);
        when(jwtHolder.getRequiredEmail()).thenReturn("password@user.com");

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> service.updateUserPassword(passUpdate));

        verify(authClient).authenticate("password@user.com", "currentPassword123!");
        verify(userCredentialsDAO, never()).updateUserPassword(anyInt(), any());
        verify(userProfileClient, never()).getCurrentUser();
        assertEquals("[Assertion failed] - this String argument must have length; it must not be null or empty",
                e.getMessage(), "Exception Message");
    }

    @Test
    public void testUpdateUserPasswordByIdValid() {

    }

    @Test
    public void testUpdateUserPasswordByIdUserHasInsufficientPermissions() {

    }

    @Test
    public void testResetUserPasswordValid() {

    }

    @Test
    public void testResetUserPasswordInvalidResetPasswordToken() {

    }
}
