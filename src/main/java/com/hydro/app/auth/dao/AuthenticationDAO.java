package com.hydro.app.auth.dao;

import javax.sql.DataSource;

import com.hydro.common.abstracts.BaseDao;
import com.hydro.common.exceptions.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Repository;

/**
 * Class that handles all the dao calls to the database for users
 * 
 * @author Sam Butler
 * @since June 25, 2021
 */
@Repository
public class AuthenticationDAO extends BaseDao {

    @Autowired
    public AuthenticationDAO(DataSource source) {
        super(source);
    }

    /**
     * Get the {@link BCrypt} hashed password for the given email.
     * 
     * @param email The email assocaited with the user.
     * @return {@link String} of the hashed password.
     * @throws Exception If there is not user for the given email.
     */
    public String getUserAuthPassword(String email) throws Exception {
        try {
            return get(getSql("getUserHashedPassword"), parameterSource(EMAIL, email), String.class);
        } catch (Exception e) {
            throw new NotFoundException("User Email", email);
        }
    }
}