package com.hydro.common.exceptions.helper;

import com.hydro.common.exceptions.InvalidCredentialsException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Exception Helper class for returning response entitys of the errored objects.
 * 
 * @author Sam Butler
 * @since August 24, 2021
 */
// @ControllerAdvice
public class ExceptionHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHelper.class);

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ExceptionError> handleInvalidCredentialsException(Exception ex) {
        LOGGER.error(ex.getMessage());
        return new ResponseEntity<ExceptionError>(new ExceptionError(ex.getMessage(), HttpStatus.UNAUTHORIZED),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionError> handleException(Exception ex) {
        LOGGER.error(ex.getMessage());
        return new ResponseEntity<ExceptionError>(new ExceptionError(ex.getMessage(), HttpStatus.BAD_REQUEST),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
