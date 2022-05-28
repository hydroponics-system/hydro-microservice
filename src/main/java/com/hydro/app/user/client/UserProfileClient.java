package com.hydro.app.user.client;

import java.util.List;

import com.hydro.annotations.interfaces.Client;
import com.hydro.app.user.client.domain.User;
import com.hydro.app.user.client.domain.request.UserGetRequest;
import com.hydro.app.user.service.ManageUserProfileService;
import com.hydro.app.user.service.UserProfileService;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * This class exposes the user endpoint's to other app's to pull data across the
 * platform.
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Client
public class UserProfileClient {

	@Autowired
	private UserProfileService userProfileService;

	@Autowired
	private ManageUserProfileService manageUserProfileService;

	/**
	 * Get users based on given request filter.
	 * 
	 * @param request of the user
	 * @return User profile object {@link User}
	 * @throws Exception
	 */
	public List<User> getUsers(UserGetRequest request) throws Exception {
		return userProfileService.getUsers(request);
	}

	/**
	 * Gets the current logged in user information.
	 * 
	 * @return The user currently logged in.
	 * @throws Exception
	 */
	public User getCurrentUser() throws Exception {
		return userProfileService.getCurrentUser();
	}

	/**
	 * Client method to get the user given a user id
	 * 
	 * @param id of the user
	 * @return User profile object
	 * @throws Exception
	 */
	public User getUserById(int id) throws Exception {
		return userProfileService.getUserById(id);
	}

	/**
	 * Creates a new user.
	 * 
	 * @param user The user to be created.
	 * @return The user that would be created.
	 * @throws Exception
	 */
	public User createUser(User user) throws Exception {
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
	public User updateUserProfile(User user) throws Exception {
		return manageUserProfileService.updateUserProfile(user);
	}

	/**
	 * Updates a user for the given id.
	 * 
	 * @param id of the user
	 * @return user associated to that id with the updated information
	 * @throws Exception
	 */
	public User updateUserProfileById(int id, User user) throws Exception {
		return manageUserProfileService.updateUserProfileById(id, user);
	}

	/**
	 * Method that will update the user's last login time to current date and time;
	 * 
	 * @param userId The user Id to be updated.
	 * @return The user object with the updated information.
	 * @throws Exception
	 */
	public User updateUserLastLoginToNow(int id) throws Exception {
		return manageUserProfileService.updateUserLastLoginToNow(id);
	}

	/**
	 * Delete the user for the given id.
	 * 
	 * @param id The id of the user being deleted
	 */
	public void deleteUser(int id) throws Exception {
		manageUserProfileService.deleteUser(id);
	}
}
