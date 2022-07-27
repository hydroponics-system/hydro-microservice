package com.hydro.insite_common_microservice.annotations.interfaces;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.stereotype.Component;

import com.hydro.insite_user_microservice.client.domain.enums.WebRole;

/**
 * Default annotation for client classes.
 * 
 * @author Sam Butler
 * @since July 31, 2021
 */
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface ControllerJwt {
    int userId() default 1;

    String firstName() default "Auth";

    String lastName() default "User";

    String email() default "test@user.com";

    WebRole webRole() default WebRole.ADMIN;
}