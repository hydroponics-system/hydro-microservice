package com.hydro.insite_common_microservice.exceptions.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.hydro.insite_common_microservice.exceptions.BaseException;
import com.hydro.insite_common_microservice.exceptions.InvalidCredentialsException;
import com.hydro.insite_common_microservice.util.HydroLogger;

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
        return new ResponseEntity<ExceptionError>(new ExceptionError(ex.getMessage()),
                                                  HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ExceptionError> handleBaseException(Exception ex) {
        LOGGER.error(ex.getMessage());
        return new ResponseEntity<ExceptionError>(new ExceptionError(ex.getMessage()),
                                                  HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionError> handleException(Exception ex) {
        LOGGER.error(ex.getMessage());
        return new ResponseEntity<ExceptionError>(new ExceptionError(ex.getMessage()),
                                                  HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
