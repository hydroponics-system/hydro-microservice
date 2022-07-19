package com.hydro.common.exceptions.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.hydro.common.exceptions.InvalidCredentialsException;
import com.hydro.common.util.HydroLogger;

/**
 * Exception Helper class for returning response entitys of the errored objects.
 * 
 * @author Sam Butler
 * @since August 24, 2021
 */
@ControllerAdvice
public class ExceptionHelper {

    @Autowired
    private HydroLogger LOGGER;

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ExceptionError> handleInvalidCredentialsException(Exception ex) {
        LOGGER.error(ex.getMessage());
        return new ResponseEntity<ExceptionError>(new ExceptionError(ex.getMessage(), HttpStatus.UNAUTHORIZED),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionError> handleException(Exception ex) {
        LOGGER.error(ex.getMessage());
        return new ResponseEntity<ExceptionError>(new ExceptionError(ex.getMessage(), HttpStatus.FORBIDDEN),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
