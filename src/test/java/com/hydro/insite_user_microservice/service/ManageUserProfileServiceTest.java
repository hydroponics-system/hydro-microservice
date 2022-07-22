package com.hydro.insite_user_microservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.hydro.factory.annotations.HydroServiceTest;
import com.hydro.factory.data.UserFactoryData;
import com.hydro.insite_common_microservice.exceptions.InsufficientPermissionsException;
import com.hydro.insite_common_microservice.exceptions.NotFoundException;
import com.hydro.insite_common_microservice.util.HydroLogger;
import com.hydro.insite_jwt_microservice.utility.JwtHolder;
import com.hydro.insite_user_microservice.client.UserCredentialsClient;
import com.hydro.insite_user_microservice.client.domain.User;
import com.hydro.insite_user_microservice.client.domain.enums.WebRole;
import com.hydro.insite_user_microservice.dao.UserProfileDAO;

@HydroServiceTest
public class ManageUserProfileServiceTest {

    @Mock
    private JwtHolder jwtHolder;

    @Mock
    private UserCredentialsClient userCredentialsClient;

    @Mock
    private UserProfileDAO dao;

    @Mock
    private HydroLogger logger;

    @InjectMocks
    private ManageUserProfileService service;

    @Test
    public void testCreateUser() throws Exception {
        User newUser = new User();
        newUser.setPassword("NewUserPassword!");

        when(dao.insertUser(any(User.class))).thenReturn(1);

        service.createUser(newUser);

        verify(dao).insertUser(any(User.class));
        verify(userCredentialsClient).insertUserPassword(1, "NewUserPassword!");
    }

    @Test
    public void testUpdateUserProfile() throws Exception {
        when(jwtHolder.getUserId()).thenReturn(12);

        service.updateUserProfile(UserFactoryData.userData());

        verify(dao).updateUserProfile(eq(12), any(User.class));
    }

    @Test
    public void testUpdateUserProfileByIdValid() throws Exception {
        User userToUpdate = UserFactoryData.userData();
        userToUpdate.setWebRole(WebRole.SYSTEM_USER);

        when(dao.getUserById(anyInt())).thenReturn(userToUpdate);
        when(jwtHolder.getWebRole()).thenReturn(WebRole.ADMIN);

        service.updateUserProfileById(5, userToUpdate);

        verify(dao).updateUserProfile(eq(5), any(User.class));
    }

    @Test
    public void testUpdateUserProfileByIdUserHasInsufficientPermissions() throws Exception {
        User userToUpdate = UserFactoryData.userData();

        when(dao.getUserById(anyInt())).thenReturn(userToUpdate);
        when(jwtHolder.getWebRole()).thenReturn(WebRole.ADMIN);

        InsufficientPermissionsException e = assertThrows(InsufficientPermissionsException.class,
                () -> service.updateUserProfileById(5, userToUpdate));

        verify(dao, never()).updateUserProfile(anyInt(), any());
        assertEquals("Your role of 'ADMIN' can not update a user of role 'ADMIN'", e.getMessage(), "Exception Message");
    }

    @Test
    public void testUpdateUserLastLoginToNowValid() throws Exception {
        service.updateUserLastLoginToNow(1);
        verify(dao).updateUserLastLoginToNow(1);
    }

    @Test
    public void testUpdateUserLastLoginToNowUserDoesNotExist() throws Exception {
        when(dao.getUserById(anyInt())).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> service.updateUserLastLoginToNow(1));

        verify(dao, never()).updateUserLastLoginToNow(anyInt());
    }

    @Test
    public void testDeleteUserValid() throws Exception {
        User userToDelete = UserFactoryData.userData();
        userToDelete.setWebRole(WebRole.SYSTEM_USER);

        when(dao.getUserById(anyInt())).thenReturn(userToDelete);
        when(jwtHolder.getWebRole()).thenReturn(WebRole.ADMIN);

        service.deleteUser(5);
        verify(dao).deleteUser(5);
    }

    @Test
    public void testDeleteUserUserHasInsufficientPermissions() throws Exception {
        User userToDelete = UserFactoryData.userData();

        when(dao.getUserById(anyInt())).thenReturn(userToDelete);
        when(jwtHolder.getWebRole()).thenReturn(WebRole.ADMIN);

        InsufficientPermissionsException e = assertThrows(InsufficientPermissionsException.class,
                () -> service.deleteUser(5));

        verify(dao, never()).deleteUser(anyInt());
        assertEquals("Your role of 'ADMIN' can not delete a user of role 'ADMIN'", e.getMessage(), "Exception Message");
    }
}
