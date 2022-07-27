package com.hydro.insite_common_microservice.exceptions.helper;

import java.util.Date;

import org.springframework.http.HttpStatus;

/**
 * Custom exception object to be returned when endpoints have errors.
 * 
 * @author Sam Butler
 * @since August 24, 2021
 */
public class ExceptionError {
    private Date timestamp;

    private HttpStatus status;

    private String error;

    private String message;

    public ExceptionError() {}

    public ExceptionError(String message) {
        this.status = HttpStatus.BAD_REQUEST;
        this.error = HttpStatus.BAD_REQUEST.getReasonPhrase();
        this.timestamp = new Date();
        this.message = message;
    }

    public ExceptionError(String message, HttpStatus status) {
        this.status = status;
        this.error = status.getReasonPhrase();
        this.timestamp = new Date();
        this.message = message;
    }

    public ExceptionError(Date timestamp, HttpStatus status, String error, String message) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
