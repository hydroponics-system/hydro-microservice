package com.hydro.factory.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Annotation id for test that deal with service classes.
 * 
 * @author Sam Butler
 * @since July 31, 2021
 */
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(MockitoExtension.class)
public @interface HydroServiceTest {}