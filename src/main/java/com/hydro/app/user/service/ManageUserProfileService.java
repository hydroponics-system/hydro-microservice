package com.hydro.app.user.service;

import com.hydro.app.user.client.UserCredentialsClient;
import com.hydro.app.user.client.domain.User;
import com.hydro.app.user.dao.UserProfileDAO;
import com.hydro.common.exceptions.InsufficientPermissionsException;
import com.hydro.jwt.utility.JwtHolder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * User Service class that handles all service calls to the dao
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Transactional
@Service
public class ManageUserProfileService {
	@Autowired
	private JwtHolder jwtHolder;

	@Autowired
	private UserCredentialsClient userCredentialsClient;

	@Autowired
	private UserProfileDAO dao;

	/**
	 * Creates a new user for the given user object.
	 * 
	 * @param user The user to create.
	 * @return {@link User} object of the users data.
	 * @throws Exception
	 */
	public User createUser(User user) throws Exception {
		int newUserId = dao.insertUser(user);
		userCredentialsClient.insertUserPassword(newUserId, user.getPassword());
		return dao.getUserById(newUserId);
	}

	/**
	 * Update the user profile for the given user object.
	 * 
	 * @param user what information on the user needs to be updated.
	 * @return user associated to that id with the updated information
	 * @throws Exception
	 */
	public User updateUserProfile(User user) throws Exception {
		return updateUserProfile(jwtHolder.getRequiredUserId(), user);
	}

	/**
	 * Updates a user for the given id.
	 * 
	 * @param id of the user
	 * @return user associated to that id with the updated information
	 * @throws Exception
	 */
	public User updateUserProfileById(int id, User user) throws Exception {
		User updatingUser = dao.getUserById(id);
		if (id != updatingUser.getId() && jwtHolder.getWebRole().getRank() <= updatingUser.getWebRole().getRank()) {
			throw new InsufficientPermissionsException(jwtHolder.getWebRole(), updatingUser.getWebRole(), "update");
		}

		return updateUserProfile(id, user);
	}

	/**
	 * Method that will update the user's last login time to current date and time;
	 * 
	 * @param userId The user Id to be updated.
	 * @return The user object with the updated information.
	 * @throws Exception
	 */
	public User updateUserLastLoginToNow(int userId) throws Exception {
		dao.getUserById(userId);
		return dao.updateUserLastLoginToNow(userId);
	}

	/**
	 * Delete the user for the given id.
	 * 
	 * @param id The id of the user being deleted
	 * @throws Exception
	 */
	public void deleteUser(int id) throws Exception {
		User deletingUser = dao.getUserById(id);
		if (id != deletingUser.getId() && jwtHolder.getWebRole().getRank() <= deletingUser.getWebRole().getRank()) {
			throw new InsufficientPermissionsException(jwtHolder.getWebRole(), deletingUser.getWebRole(), "delete");
		}
		dao.deleteUser(id);
	}

	/**
	 * Update the user for the given user object.
	 * 
	 * @param user what information on the user needs to be updated.
	 * @return user associated to that id with the updated information
	 * @throws Exception
	 */
	private User updateUserProfile(int userId, User user) throws Exception {
		return dao.updateUserProfile(userId, user);
	}
}
