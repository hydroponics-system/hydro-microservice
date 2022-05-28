package com.hydro.factory.data;

import java.time.LocalDateTime;

import com.hydro.app.user.client.domain.User;
import com.hydro.app.user.client.domain.enums.WebRole;

/**
 * Class for holding common test data to be used in test classes.
 * 
 * @author Sam Butler
 * @since May 26, 2022
 */
public class UserFactoryData {

    /**
     * Gets a default user object.
     * 
     * @return {@link User} object for testing.
     */
    public static User userData() {
        User u = new User();
        u.setId(12);
        u.setFirstName("Test");
        u.setLastName("User");
        u.setEmail("test@user.com");
        u.setWebRole(WebRole.ADMIN);
        u.setLastLoginDate(LocalDateTime.now());
        return u;
    }
}
