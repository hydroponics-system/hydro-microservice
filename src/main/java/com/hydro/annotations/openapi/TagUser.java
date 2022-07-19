package com.hydro.annotations.openapi;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Tag User Profile
 *
 * @author Sam Butler
 * @since July 19, 2022
 */
@Retention(RUNTIME)
@Target({ TYPE, METHOD, ANNOTATION_TYPE })
@Inherited
@Tag(name = "User")
public @interface TagUser {
}