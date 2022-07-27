package com.hydro.insite_auth_microservice.client.domain;

import java.time.LocalDateTime;

import com.hydro.insite_user_microservice.client.domain.User;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Authentication token to be used within the app.
 *
 * @author Sam Butler
 * @since July 31, 2021
 */
@Schema(description = "User Auth Token")
public class AuthToken {

    @Schema(description = "JWT Token for the user.")
    private String token;

    @Schema(description = "When the token was created.")
    private LocalDateTime createDate;

    @Schema(description = "When the token expires.")
    private LocalDateTime expireDate;

    @Schema(description = "Authenticated User.")
    private User user;

    public AuthToken() {}

    public AuthToken(String t, LocalDateTime creation, LocalDateTime expire, User u) {
        token = t;
        expireDate = expire;
        createDate = creation;
        user = u;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDateTime expireDate) {
        this.expireDate = expireDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
