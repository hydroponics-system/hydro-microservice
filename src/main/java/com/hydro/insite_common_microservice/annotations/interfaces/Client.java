package com.hydro.insite_common_microservice.annotations.interfaces;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.stereotype.Component;

/**
 * Default annotation for client classes.
 * 
 * @author Sam Butler
 * @since July 31, 2021
 */
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface Client {}
