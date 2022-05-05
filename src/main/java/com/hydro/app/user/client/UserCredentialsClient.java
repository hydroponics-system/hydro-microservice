package com.hydro.app.user.client;

import com.hydro.annotations.interfaces.Client;
import com.hydro.app.user.client.domain.PasswordUpdate;
import com.hydro.app.user.client.domain.User;
import com.hydro.app.user.service.UserCredentialsService;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * This class exposes the user endpoint's to other app's to pull data across the
 * platform.
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Client
public class UserCredentialsClient {

    @Autowired
    private UserCredentialsService service;

    /**
     * This will create a new row in the user_credentails database with the given
     * auth password and the id of the user to assign it too. This method will only
     * be called when creating a new user.
     * 
     * @param id       The id of the new user.
     * @param authPass The password and salt that was created.
     * @throws Exception If the password can not be stored.
     */
    public void insertUserPassword(int id, String pass) throws Exception {
        service.insertUserPassword(id, pass);
    }

    /**
     * This will take in a {@link PasswordUpdate} object that will confirm that the
     * current password matches the database password. If it does then it will
     * update the password to the new password.
     * 
     * @param passUpdate Object the holds the current password and new user password
     *                   to change it too.
     * @return {@link User} object of the user that was updated.
     * @throws Exception If the user can not be authenticated or it failed to hash
     *                   the new password.
     */
    public User updateUserPassword(PasswordUpdate passUpdate) throws Exception {
        return service.updateUserPassword(passUpdate);
    }

    /**
     * This will take in a {@link PasswordUpdate} object and a user id that needs
     * the password updated.
     * 
     * @param passUpdate Object the holds the current password and new user password
     *                   to change it too.
     * @return {@link User} object of the user that was updated.
     */
    public User updateUserPasswordById(int id, PasswordUpdate passUpdate) throws Exception {
        return service.updateUserPasswordById(id, passUpdate);
    }

    /**
     * This will get called when a user has forgotten their password. This will
     * allow them to reset it.
     * 
     * @param passUpdate Object the holds the current password and new user password
     *                   to change it too.
     * @return {@link User} object of the user that was updated.
     * @throws Exception If the user can not be authenticated or it failed to hash
     *                   the new password.
     */
    public User resetUserPassword(PasswordUpdate passUpdate) throws Exception {
        return service.resetUserPassword(passUpdate.getNewPassword());
    }
}
