package com.hydro.insite_auth_microservice.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Repository;

import com.hydro.insite_common_microservice.abstracts.BaseDao;
import com.hydro.insite_common_microservice.exceptions.NotFoundException;

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
