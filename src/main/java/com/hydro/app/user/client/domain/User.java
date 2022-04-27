package com.hydro.app.user.client.domain;

import java.time.LocalDateTime;

import com.hydro.common.enums.WebRole;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Class to create a user profile object
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@ApiModel(description = "User object for holding user details.")
public class User {

	@ApiModelProperty(value = "User identifier")
	private int id;

	@ApiModelProperty(value = "First name of the user.")
	private String firstName;

	@ApiModelProperty(value = "Last name of the user.")
	private String lastName;

	@ApiModelProperty(value = "The users email")
	private String email;

	@ApiModelProperty(value = "The user web role", allowableValues = "USER,ADMIN")
	private WebRole webRole;

	@ApiModelProperty(value = "The users password (hashed).")
	private String password;

	@ApiModelProperty(value = "The date the user has last authenticated.")
	private LocalDateTime lastLoginDate;

	@ApiModelProperty(value = "When the user was created.")
	private LocalDateTime insertDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastname) {
		this.lastName = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public WebRole getWebRole() {
		return webRole;
	}

	public void setWebRole(WebRole webRole) {
		this.webRole = webRole;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public LocalDateTime getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(LocalDateTime lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public LocalDateTime getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(LocalDateTime insertDate) {
		this.insertDate = insertDate;
	}
}
