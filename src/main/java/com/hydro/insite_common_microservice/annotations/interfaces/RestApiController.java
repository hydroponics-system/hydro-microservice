package com.hydro.insite_common_microservice.annotations.interfaces;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

/**
 * Interface for adding all the annotations for a rest controller.
 * 
 * @author Sam Butler
 * @since July 31, 2021
 */
@Retention(RetentionPolicy.RUNTIME)
@CrossOrigin
@RestController
public @interface RestApiController {

}
