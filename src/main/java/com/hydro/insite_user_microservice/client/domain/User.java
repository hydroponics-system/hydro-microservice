package com.hydro.insite_user_microservice.client.domain;

import java.time.LocalDateTime;

import com.hydro.insite_user_microservice.client.domain.enums.WebRole;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Class to create a user profile object
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Schema(description = "User object for holding user details.")
public class User {

	@Schema(description = "User identifier")
	private int id;

	@Schema(description = "First name of the user.")
	private String firstName;

	@Schema(description = "Last name of the user.")
	private String lastName;

	@Schema(description = "The users email")
	private String email;

	@Schema(description = "The user web role", allowableValues = "USER,SYSTEM_USER,DEVELOPER,ADMIN")
	private WebRole webRole;

	@Schema(description = "The users password (hashed).")
	private String password;

	@Schema(description = "The date the user has last authenticated.")
	private LocalDateTime lastLoginDate;

	@Schema(description = "When the user was created.")
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
