package com.hydro.app.user.client.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Object used to update a users password. This will hold a current password and
 * new password fields.
 * 
 * @author Sam Butler
 * @since October 29, 2021
 */
@ApiModel(description = "Password update object for when a user wants to change their password.")
public class PasswordUpdate {

    @ApiModelProperty(value = "The users current password.")
    private String currentPassword;

    @ApiModelProperty(value = "The users new password")
    private String newPassword;

    public PasswordUpdate() {
    }

    public PasswordUpdate(String currentPassword, String newPassword) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
