package com.hydro.insite_subscription_microservice.client.domain;

import java.security.Principal;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * User principal for identifying users on a websocket.
 * 
 * @author Sam Butler
 * @since March 24, 2022
 */
@Schema(description = "User Principal for subscription identification.")
public class UserPrincipal implements Principal {
    private String name;

    public UserPrincipal(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
