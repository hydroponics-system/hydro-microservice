package com.hydro.app.auth.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.hydro.common.exceptions.UserNotFoundException;
import com.hydro.factory.BaseDataSourceTest;
import com.hydro.factory.annotations.HydroDaoTest;
import com.hydro.factory.config.test.AuthenticationDaoTestConfig;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

@ContextConfiguration(classes = AuthenticationDaoTestConfig.class)
@Sql("/scripts/auth/init.sql")
@HydroDaoTest
public class AuthenticationDaoTest extends BaseDataSourceTest {

    @Autowired
    private AuthenticationDao dao;

    @Test
    public void testGetUserAuthPasswordValidEmail() throws Exception {
        String hashedPass = dao.getUserAuthPassword("test@mail.com");

        assertNotNull(hashedPass, "Hashed Password not null");
        assertTrue(BCrypt.checkpw("testPassword", hashedPass), "Passwords should match");
    }

    @Test
    public void testGetUserAuthPasswordEmailNotFound() {
        UserNotFoundException e = assertThrows(UserNotFoundException.class,
                () -> dao.getUserAuthPassword("notFound@mail.com"));

        assertEquals("User not found for email: 'notFound@mail.com'", e.getMessage(), "Message should match");
    }
}
