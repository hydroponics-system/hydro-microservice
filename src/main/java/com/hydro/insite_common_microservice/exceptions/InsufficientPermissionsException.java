package com.hydro.insite_common_microservice.exceptions;

import com.hydro.insite_user_microservice.client.domain.enums.WebRole;

/**
 * Exception thrown when user does not have the permissions to access certion
 * data.
 * 
 * @author Sam Butler
 * @since July 31, 2021
 */
public class InsufficientPermissionsException extends BaseException {

    /**
     * Base Constructor for adding a custom message on a
     * {@link InsufficientPermissionsException} object
     * 
     * @param message The message to be used.
     */
    public InsufficientPermissionsException(String message) {
        super(message);
    }

    /**
     * Throws {@link InsufficientPermissionsException} for the role.
     * 
     * @param role The role to that has insufficent permissions.
     */
    public InsufficientPermissionsException(WebRole role) {
        super(String.format("Insufficient Permissions for role '%s'", role));
    }

    /**
     * Throws an insufficent permissions exception for the webrole and action.
     * 
     * @param insufficentWebRole The user that does not have sufficent permissions.
     * @param changingWebRole    The role of the user that the action was being
     *                           performed on.
     * @param action             The type of action (e.g. update, delete, get, etc)
     */
    public InsufficientPermissionsException(WebRole insufficentWebRole, WebRole changingWebRole, String action) {
        super(String.format("Your role of '%s' can not %s a user of role '%s'", insufficentWebRole, action,
                            changingWebRole));
    }
}
