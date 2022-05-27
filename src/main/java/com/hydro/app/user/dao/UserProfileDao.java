package com.hydro.app.user.dao;

import static com.hydro.app.user.mapper.UserProfileMapper.USER_MAPPER;

import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import com.google.common.collect.Sets;
import com.hydro.app.user.client.domain.User;
import com.hydro.app.user.client.domain.request.UserGetRequest;
import com.hydro.common.abstracts.BaseDao;
import com.hydro.common.exceptions.NotFoundException;
import com.hydro.common.sql.SqlParamBuilder;
import com.hydro.common.util.CommonUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

/**
 * Class that handles all the dao calls to the database for users
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Repository
public class UserProfileDAO extends BaseDao {

	@Autowired
	public UserProfileDAO(DataSource source) {
		super(source);
	}

	/**
	 * Get users based on given request filter
	 * 
	 * @param request of the user
	 * @return User profile object {@link User}
	 * @throws Exception
	 */
	public List<User> getUsers(UserGetRequest request) {
		MapSqlParameterSource params = SqlParamBuilder.with().withParam(ID, request.getId())
				.withParam(EMAIL, request.getEmail()).withParam(FIRST_NAME, request.getFirstName())
				.withParam(LAST_NAME, request.getLastName())
				.withParamTextEnumCollection(WEB_ROLE_TEXT_ID, request.getWebRole()).build();

		return getPage(getSql("getUsers", params), params, USER_MAPPER);
	}

	/**
	 * This method returns a user profile object containing profile type information
	 * about the user
	 * 
	 * @param id of the user
	 * @return User profile object {@link UserProfile}
	 * @throws Exception
	 */
	public User getUserById(int id) throws Exception {
		try {
			UserGetRequest request = new UserGetRequest();
			request.setId(Sets.newHashSet(id));
			return getUsers(request).get(0);
		} catch (Exception e) {
			throw new NotFoundException("User", id);
		}
	}

	/**
	 * Creates a new user for the given user object.
	 * 
	 * @param user The user to create.
	 * @return {@link User} object of the users data.
	 * @throws Exception
	 */
	public User insertUser(User user) throws InvalidDataAccessApiUsageException, Exception {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		MapSqlParameterSource params = SqlParamBuilder.with().withParam(FIRST_NAME, user.getFirstName())
				.withParam(LAST_NAME, user.getLastName()).withParam(EMAIL, user.getEmail())
				.withParam(WEB_ROLE_ID, user.getWebRole() != null ? user.getWebRole().getRank() : 1).build();

		post(getSql("insertUser", params), params, keyHolder);
		return getUserById(keyHolder.getKey().intValue());
	}

	/**
	 * Update the user for the given user object. Null out password field so that it
	 * is not returned on the {@link User} object
	 * 
	 * @param userId Id of the usre being updated.
	 * @param user   what information on the user needs to be updated.
	 * @return user associated to that id with the updated information
	 * @throws Exception
	 */
	public User updateUserProfile(int userId, User user) throws Exception {
		User userProfile = getUserById(userId);
		user = mapNonNullUserFields(user, userProfile);

		MapSqlParameterSource params = SqlParamBuilder.with().withParam(FIRST_NAME, user.getFirstName())
				.withParam(LAST_NAME, user.getLastName()).withParam(EMAIL, user.getEmail())
				.withParam(WEB_ROLE_ID, user.getWebRole().getRank()).withParam(ID, userId).build();

		update(getSql("updateUserProfile", params), params);

		return getUserById(userId);
	}

	/**
	 * Method that will update the user's last login time to current date and time;
	 * 
	 * @param userId The user Id to be updated.
	 * @return The user object with the updated information.
	 * @throws Exception
	 */
	public User updateUserLastLoginToNow(int userId) throws Exception {
		MapSqlParameterSource params = SqlParamBuilder.with()
				.withParam(LAST_LOGIN_DATE, CommonUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"))
				.withParam(ID, userId).build();

		update(getSql("updateUserLastLoginToNow", params), params);

		return getUserById(userId);
	}

	/**
	 * Delete the user for the given id.
	 * 
	 * @param id The id of the user being deleted
	 */
	public void deleteUser(int id) {
		delete(getSql("deleteUser"), parameterSource(ID, id));
	}

	/**
	 * Maps non null user fields from the source to the desitnation.
	 * 
	 * @param destination Where the null fields should be replaced.
	 * @param source      Where to get the replacements for the null fields.
	 * @return {@link User} with the replaced fields.
	 */
	private User mapNonNullUserFields(User destination, User source) {
		if (destination.getFirstName() == null)
			destination.setFirstName(source.getFirstName());
		if (destination.getLastName() == null)
			destination.setLastName(source.getLastName());
		if (destination.getEmail() == null)
			destination.setEmail(source.getEmail());
		if (destination.getWebRole() == null)
			destination.setWebRole(source.getWebRole());
		return destination;
	}
}
