package com.hydro.insite_user_microservice.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hydro.insite_common_microservice.annotations.interfaces.HasAccess;
import com.hydro.insite_user_microservice.client.domain.User;
import com.hydro.insite_user_microservice.client.domain.enums.WebRole;
import com.hydro.insite_user_microservice.client.domain.request.UserGetRequest;
import com.hydro.insite_user_microservice.openapi.TagUser;
import com.hydro.insite_user_microservice.service.ManageUserProfileService;
import com.hydro.insite_user_microservice.service.UserProfileService;

import io.swagger.v3.oas.annotations.Operation;

@RequestMapping("/api/user-app/profile")
@RestController
@TagUser
public class UserProfileController {

	@Autowired
	private UserProfileService userProfileService;

	@Autowired
	private ManageUserProfileService manageUserProfileService;

	/**
	 * Gets a list of users based of the request filter
	 * 
	 * @param request to filter on
	 * @return list of user objects
	 * @throws Exception
	 */
	@Operation(summary = "Get a list of users.", description = "Given a User Get Request, it will return a list of users that match the request.")
	@GetMapping(produces = APPLICATION_JSON_VALUE)
	@HasAccess(WebRole.ADMIN)
	public List<User> getUsers(UserGetRequest request) throws Exception {
		return userProfileService.getUsers(request);
	}

	/**
	 * Gets the current logged in user information.
	 * 
	 * @return The user currently logged in.
	 * @throws Exception
	 */
	@Operation(summary = "Gets current user of the session call.", description = "Will return the current user based on the active session jwt holder.")
	@GetMapping(path = "/current-user", produces = APPLICATION_JSON_VALUE)
	public User getCurrentUser() throws Exception {
		return userProfileService.getCurrentUser();
	}

	/**
	 * Get user object for the given Id
	 * 
	 * @param id of the user
	 * @return user associated to that id
	 * @throws Exception
	 */
	@Operation(summary = "Gets a user by id.", description = "For the given id value, it will return the corresponding user.")
	@GetMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
	@HasAccess(WebRole.ADMIN)
	public User getUserById(@PathVariable int id) throws Exception {
		return userProfileService.getUserById(id);
	}

	/**
	 * This will check to see if the email exists. If it does then it will return
	 * true, otherwise false.
	 * 
	 * @param email The email to check
	 * @return {@link Boolean} to see if the email exists
	 * @throws Exception
	 */
	@Operation(summary = "Checks to see if email exist.", description = "Takes in a email to check to see if it exists in the database.")
	@GetMapping("/check-email")
	public boolean doesEmailExist(@RequestParam String email) throws Exception {
		return userProfileService.doesEmailExist(email);
	}

	/**
	 * Creates a new user for the given user object.
	 * 
	 * @param user The user to create.
	 * @return {@link User} object of the users data.
	 * @throws Exception
	 */
	@Operation(summary = "Create a new user.", description = "Takes in a user object and will insert them into the database.")
	@PostMapping(produces = APPLICATION_JSON_VALUE)
	public User createUser(@RequestBody User user) throws Exception {
		return manageUserProfileService.createUser(user);
	}

	/**
	 * Update the user's information such as email, first name, last name, and
	 * password
	 * 
	 * @param user what information on the user needs to be updated.
	 * @return user associated to that id with the updated information
	 * @throws Exception
	 */
	@Operation(summary = "Updates a user information.", description = "Takes in a user object and updates the current user information.")
	@PutMapping(produces = APPLICATION_JSON_VALUE)
	public User updateUserProfile(@RequestBody User user) throws Exception {
		return manageUserProfileService.updateUserProfile(user);
	}

	/**
	 * Updates a user for the given id.
	 * 
	 * @param id of the user
	 * @return user associated to that id with the updated information
	 * @throws Exception
	 */
	@Operation(summary = "Updates a user information by id.", description = "Updates a user information by given user id. Admin only endpoint.")
	@PutMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
	@HasAccess(WebRole.ADMIN)
	public User updateUserProfileById(@PathVariable int id, @RequestBody User user) throws Exception {
		return manageUserProfileService.updateUserProfileById(id, user);
	}

	/**
	 * Delete the user for the given id.
	 * 
	 * @param id The id of the user being deleted
	 * @throws Exception
	 */
	@Operation(summary = "Delete a user by id.", description = "Delete a user for the given user id. Admin only endpoint.")
	@DeleteMapping("/{id}")
	@HasAccess(WebRole.ADMIN)
	public void deleteUser(@PathVariable int id) throws Exception {
		manageUserProfileService.deleteUser(id);
	}
}