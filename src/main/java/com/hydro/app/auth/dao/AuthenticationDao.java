package com.hydro.app.auth.dao;

import javax.sql.DataSource;

import com.hydro.common.abstracts.BaseDao;
import com.hydro.common.exceptions.UserNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Class that handles all the dao calls to the database for users
 * 
 * @author Sam Butler
 * @since June 25, 2021
 */
@Repository
public class AuthenticationDao extends BaseDao {

    @Autowired
    public AuthenticationDao(DataSource source) {
        super(source);
    }

    /**
     * Get the hashed password and salt value associated to the user.
     * 
     * @param email The email assocaited with the user.
     * @return {@link long} of the 10 digit salt value.
     * @throws Exception If there is not user for the given email.
     */
    public String getUserAuthPassword(String email) throws Exception {
        try {
            return get(getSql("getUserHashedPassword"), parameterSource(EMAIL, email), String.class);
        } catch (Exception e) {
            throw new UserNotFoundException(String.format("User not found for email: %s", email));
        }
    }
}
