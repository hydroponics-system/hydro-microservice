package com.hydro.app.user.client.domain.request;

import java.util.Set;

import com.hydro.common.enums.WebRole;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * This class handles lookups passed to the DAO.
 *
 * @author Sam Butler
 * @since September 9, 2021
 */
@ApiModel(description = "User get request object for filtering users.")
public class UserGetRequest {

    @ApiModelProperty(value = "List of user ids.")
    private Set<Integer> id;

    @ApiModelProperty(value = "List of user first names.")
    private Set<String> firstName;

    @ApiModelProperty(value = "List of user last names.")
    private Set<String> lastName;

    @ApiModelProperty(value = "List of emails.")
    private Set<String> email;

    @ApiModelProperty(value = "List of user web roles.", allowableValues = "USER,ADMIN")
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