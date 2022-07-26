package com.hydro.insite_auth_microservice.client.domain.request;

import java.io.Serializable;

import javax.annotation.Nonnull;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * AuthenticationRequest for authenticating and updating user credentials.
 *
 * @author Sam Butler
 * @since August 1, 2020
 */
@Schema(description = "Authentication Request")
public class AuthenticationRequest implements Serializable {

    @Schema(description = "The email to authenticate with.")
    @Nonnull
    private String email;

    @Schema(description = "The password associated with the email.")
    @Nonnull
    private String password;

    public AuthenticationRequest() {}

    public AuthenticationRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
