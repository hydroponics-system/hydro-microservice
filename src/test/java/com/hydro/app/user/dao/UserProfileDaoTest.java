package com.hydro.app.user.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.hydro.app.user.client.domain.User;
import com.hydro.common.enums.WebRole;
import com.hydro.factory.BaseDataSourceTest;
import com.hydro.factory.annotations.HydroDaoTest;
import com.hydro.factory.config.test.UserProfileDaoTestConfig;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

@Sql("classpath:scripts/UserProfileDaoTest__data.sql")
@Sql(scripts = { "classpath:scripts/UserProfileDaoTest__clean.sql" }, executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
@ContextConfiguration(classes = UserProfileDaoTestConfig.class)
@HydroDaoTest
public class UserProfileDaoTest extends BaseDataSourceTest {

    @Autowired
    private UserProfileDao userDao;

    @Test
    public void testGetUserById() throws Exception {
        User user = userDao.getUserById(1);

        assertEquals("test", user.getFirstName(), "First name");
        assertEquals("user", user.getLastName(), "Last name");
        assertEquals("test@mail.com", user.getEmail(), "Email");
        assertEquals(WebRole.USER, user.getWebRole(), "Web Role");
    }
}
