package com.hydro.app.user.service;

import java.security.NoSuchAlgorithmException;

import com.hydro.app.auth.client.AuthenticationClient;
import com.hydro.app.user.client.UserProfileClient;
import com.hydro.app.user.client.domain.PasswordUpdate;
import com.hydro.app.user.client.domain.User;
import com.hydro.app.user.dao.UserCredentialsDAO;
import com.hydro.common.exceptions.BaseException;
import com.hydro.common.exceptions.InsufficientPermissionsException;
import com.hydro.jwt.utility.JwtHolder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.jsonwebtoken.lang.Assert;

/**
 * User Service class that handles all service calls to the
 * {@link UserCredentialsDAO}
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Transactional
@Service
public class UserCredentialsService {

    @Autowired
    private JwtHolder jwtHolder;

    @Autowired
    private AuthenticationClient authClient;

    @Autowired
    private UserProfileClient userProfileClient;

    @Autowired
    private UserCredentialsDAO dao;

    /**
     * This will be called when new users are created so that they have default
     * passwords. This will only be called when someone else is creating a user
     * account for someone.
     * 
     * @param userId   The id to add the password for.
     * @param authPass Contains the hashed password and salt value.
     * @throws Exception
     */
    public void insertUserPassword(int userId, String pass) {
        dao.insertUserPassword(userId, BCrypt.hashpw(pass, BCrypt.gensalt()));
    }

    /**
     * This will take in a {@link PasswordUpdate} object that will confirm that the
     * current password matches the database password. If it does then it will
     * update the password to the new password.
     * 
     * @param passUpdate Object the holds the current password and new user password
     *                   to change it too.
     * @return {@link User} object of the user that was updated.
     * @throws Exception If the user can not be authenticated or the function was
     *                   not able to hash the new password.
     */
    public User updateUserPassword(PasswordUpdate passUpdate) throws Exception {
        authClient.authenticate(jwtHolder.getRequiredEmail(), passUpdate.getCurrentPassword());
        return passwordUpdate(jwtHolder.getRequiredUserId(), passUpdate.getNewPassword());
    }

    /**
     * Method that will take in an id and a PasswordUpdate object
     * 
     * @param passUpdate Object the holds the current password and new user password
     *                   to change it too.
     * @return {@link User} object of the user that was updated.
     * @throws Exception If the user can not be authenticated or the function was
     *                   not able to hash the new password.
     */
    public User updateUserPasswordById(int userId, PasswordUpdate passUpdate) throws Exception {
        User updatingUser = userProfileClient.getUserById(userId);
        if (userId != updatingUser.getId() && jwtHolder.getWebRole().getRank() <= updatingUser.getWebRole().getRank()) {
            throw new InsufficientPermissionsException(jwtHolder.getWebRole(), updatingUser.getWebRole(), "update");
        }
        return passwordUpdate(userId, passUpdate.getNewPassword());
    }

    /**
     * This will get called when a user has forgotten their password. This will
     * allow them to reset it.
     * 
     * @param passUpdate Object the holds the current password and new user password
     *                   to change it too.
     * @return {@link User} object of the user that was updated.
     * @throws Exception If the user can not be authenticated or the function was
     *                   not able to hash the new password.
     */
    public User resetUserPassword(String pass) throws Exception {
        if (!jwtHolder.getRequiredResetPassword()) {
            throw new Exception("Invalid token for reset password!");
        }
        return passwordUpdate(jwtHolder.getRequiredUserId(), pass);
    }

    /**
     * Update the users credentials.
     * 
     * @param userId   Id of the user wanting to update their password.
     * @param password THe password to update on the user's account.
     * @return user associated to that id with the updated information
     * @throws Exception
     */
    private User passwordUpdate(int userId, String password) throws Exception {
        Assert.hasLength(password);
        try {
            dao.updateUserPassword(userId, BCrypt.hashpw(password, BCrypt.gensalt()));
            return userProfileClient.getUserById(userId);
        } catch (NoSuchAlgorithmException e) {
            throw new BaseException("Could not update password!");
        }
    }
}
