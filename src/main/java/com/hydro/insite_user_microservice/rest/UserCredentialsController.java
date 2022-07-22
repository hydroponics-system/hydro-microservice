package com.hydro.insite_user_microservice.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hydro.insite_common_microservice.annotations.interfaces.HasAccess;
import com.hydro.insite_user_microservice.client.domain.PasswordUpdate;
import com.hydro.insite_user_microservice.client.domain.User;
import com.hydro.insite_user_microservice.client.domain.enums.WebRole;
import com.hydro.insite_user_microservice.openapi.TagUser;
import com.hydro.insite_user_microservice.service.UserCredentialsService;

@RequestMapping("/api/user-app/user-credentials")
@RestController
@TagUser
public class UserCredentialsController {

    @Autowired
    private UserCredentialsService service;

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
    @PutMapping(path = "/password", produces = APPLICATION_JSON_VALUE)
    public User updateUserPassword(@RequestBody PasswordUpdate passUpdate) throws Exception {
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
    @PutMapping(path = "/password/{id}", produces = APPLICATION_JSON_VALUE)
    @HasAccess(WebRole.ADMIN)
    public User updateUserPasswordById(@PathVariable int id, @RequestBody PasswordUpdate passUpdate) throws Exception {
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
    @PutMapping(path = "/password/reset", produces = APPLICATION_JSON_VALUE)
    public User resetUserPassword(@RequestBody PasswordUpdate passUpdate) throws Exception {
        return service.resetUserPassword(passUpdate.getNewPassword());
    }
}
