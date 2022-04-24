package com.hydro.common.exceptions;

/**
 * Exception thrown when user does not have the permissions to access certion
 * data.
 * 
 * @author Sam Butler
 * @since July 31, 2021
 */
public class InsufficientPermissionsException extends BaseException {
    public InsufficientPermissionsException(String message) {
        super(message);
    }
}
