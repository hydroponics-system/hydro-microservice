package com.hydro.factory.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.hydro.factory.resolver.ActiveProfileRestTestResolver;

/**
 * Annotation id for test that deal with dao classes.
 * 
 * @author Sam Butler
 * @since July 31, 2021
 */
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles(resolver = ActiveProfileRestTestResolver.class)
public @interface HydroRestTest {}