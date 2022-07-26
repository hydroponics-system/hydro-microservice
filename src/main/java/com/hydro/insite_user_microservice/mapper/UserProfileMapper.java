package com.hydro.insite_user_microservice.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hydro.insite_common_microservice.abstracts.AbstractMapper;
import com.hydro.insite_user_microservice.client.domain.User;
import com.hydro.insite_user_microservice.client.domain.enums.WebRole;

/**
 * Mapper class to map a User Profile Object {@link User}
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
public class UserProfileMapper extends AbstractMapper<User> {
	public static UserProfileMapper USER_MAPPER = new UserProfileMapper();

	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();
		user.setId(rs.getInt(ID));
		user.setFirstName(rs.getString(FIRST_NAME));
		user.setLastName(rs.getString(LAST_NAME));
		user.setEmail(rs.getString(EMAIL));
		user.setWebRole(WebRole.valueOf(rs.getString(WEB_ROLE)));

		try {
			user.setPassword(rs.getString(PASSWORD));
		}
		catch(Exception e) {
			user.setPassword(null);
		}

		user.setLastLoginDate(rs.getTimestamp(LAST_LOGIN_DATE).toLocalDateTime());
		user.setInsertDate(rs.getTimestamp(INSERT_DATE).toLocalDateTime());
		return user;
	}
}
