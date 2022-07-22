package com.hydro.insite_user_microservice.client.domain.request;

import java.util.Set;

import com.hydro.insite_user_microservice.client.domain.enums.WebRole;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * This class handles lookups passed to the DAO.
 *
 * @author Sam Butler
 * @since September 9, 2021
 */
@Schema(description = "User get request object for filtering users.")
public class UserGetRequest {

    @Schema(description = "List of user ids.")
    private Set<Integer> id;

    @Schema(description = "List of user first names.")
    private Set<String> firstName;

    @Schema(description = "List of user last names.")
    private Set<String> lastName;

    @Schema(description = "List of emails.")
    private Set<String> email;

    @Schema(description = "List of user web roles.", allowableValues = "USER,ADMIN")
    private Set<WebRole> webRole;

    public Set<Integer> getId() {
        return id;
    }

    public void setId(Set<Integer> id) {
        this.id = id;
    }

    public Set<String> getFirstName() {
        return firstName;
    }

    public void setFirstName(Set<String> firstName) {
        this.firstName = firstName;
    }

    public Set<String> getLastName() {
        return lastName;
    }

    public void setLastName(Set<String> lastName) {
        this.lastName = lastName;
    }

    public Set<String> getEmail() {
        return email;
    }

    public void setEmail(Set<String> email) {
        this.email = email;
    }

    public Set<WebRole> getWebRole() {
        return webRole;
    }

    public void setWebRole(Set<WebRole> webRole) {
        this.webRole = webRole;
    }
}