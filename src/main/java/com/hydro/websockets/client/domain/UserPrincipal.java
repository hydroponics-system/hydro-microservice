package com.hydro.websockets.client.domain;

import java.security.Principal;

/**
 * User principal for identifying users on a websocket.
 * 
 * @author Sam Butler
 * @since March 24, 2022
 */
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
