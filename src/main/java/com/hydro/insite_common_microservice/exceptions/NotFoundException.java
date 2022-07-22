package com.hydro.insite_common_microservice.exceptions;

/**
 * Exception thrown when a object can not be found.
 * 
 * @author Sam Butler
 * @since July 31, 2021
 */
public class NotFoundException extends BaseException {

    public NotFoundException(String field, Object value) {
        super(String.format("%s not found for id: '%s'", field, value));
    }
}
