package com.hydro.app.user.dao;

import com.hydro.factory.annotations.HydroDaoTest;
import com.hydro.factory.config.test.UserDaoTestConfig;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

@ContextConfiguration(classes = UserDaoTestConfig.class)
@Sql("/scripts/user/userCredentialsDao/init.sql")
@HydroDaoTest
public class UserCredentialsDaoTest {

    @Autowired
    private UserCredentialsDao dao;

    @Test
    public void testInsertUserPassword() throws Exception {
        dao.insertUserPassword(2, BCrypt.hashpw("TestPassword!", BCrypt.gensalt()));
    }
}
