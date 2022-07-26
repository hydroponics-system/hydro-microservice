package com.hydro.insite_user_microservice.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;

import com.hydro.factory.annotations.HydroDaoTest;
import com.hydro.factory.config.test.UserDAOTestConfig;

@ContextConfiguration(classes = UserDAOTestConfig.class)
@Sql("/scripts/user/userCredentialsDAO/init.sql")
@HydroDaoTest
public class UserCredentialsDAOTest {

        @Autowired
        private UserCredentialsDAO dao;

        @Autowired
        private JdbcTemplate jdbcTemplate;

        @Test
        public void testInsertUserPasswordValidUserId() throws Exception {
                assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "user_credentials"),
                             "Row count should be 1 before insert");

                dao.insertUserPassword(2, BCrypt.hashpw("TestPassword!", BCrypt.gensalt()));

                assertEquals(2, JdbcTestUtils.countRowsInTable(jdbcTemplate, "user_credentials"),
                             "Row count should be 2 after insert");
        }

        @Test
        public void testInsertUserPasswordInvalidUserId() {
                DataIntegrityViolationException e = assertThrows(DataIntegrityViolationException.class, () -> dao
                                .insertUserPassword(200, BCrypt.hashpw("TestPassword!", BCrypt.gensalt())));

                assertTrue(e.getLocalizedMessage().contains("foreign key constraint fails"), "Exception Message");
        }

        @Test
        public void testUpdateUserPassword() {
                String newPassword = BCrypt.hashpw("TestPasswordUpdate!", BCrypt.gensalt());

                assertEquals(0,
                             JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "user_credentials",
                                                                 String.format("password='%s'", newPassword)),
                             "Password should not exist");

                dao.updateUserPassword(1, newPassword);

                assertEquals(1,
                             JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "user_credentials",
                                                                 String.format("password='%s'", newPassword)),
                             "Password should be updated");
        }
}
