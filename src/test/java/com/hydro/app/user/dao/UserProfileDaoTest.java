package com.hydro.app.user.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import com.google.common.collect.Sets;
import com.hydro.app.user.client.domain.User;
import com.hydro.app.user.client.domain.request.UserGetRequest;
import com.hydro.common.enums.WebRole;
import com.hydro.common.exceptions.UserNotFoundException;
import com.hydro.factory.BaseDataSourceTest;
import com.hydro.factory.annotations.HydroDaoTest;
import com.hydro.factory.config.test.UserProfileDaoTestConfig;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

@ContextConfiguration(classes = UserProfileDaoTestConfig.class)
@Sql("/scripts/user/userProfileDao/init.sql")
@HydroDaoTest
public class UserProfileDaoTest extends BaseDataSourceTest {

    @Autowired
    private UserProfileDao dao;

    @Test
    public void testGetUserList() throws Exception {
        List<User> user = dao.getUsers(new UserGetRequest());

        assertEquals(3, user.size(), "User Size should be 3");
        assertEquals("Fake", user.get(0).getFirstName(), "User 1 first name");
        assertEquals("Bill", user.get(1).getFirstName(), "User 2 first name");
        assertEquals("Test", user.get(2).getFirstName(), "User 3 first name");
    }

    @Test
    public void testGetUserListWithFilter() throws Exception {
        UserGetRequest request = new UserGetRequest();
        request.setWebRole(Sets.newHashSet(WebRole.USER));
        List<User> user = dao.getUsers(request);

        assertEquals(2, user.size(), "User Size should be 2");
        assertEquals("Bill", user.get(0).getFirstName(), "User 1 first name");
        assertEquals("Test", user.get(1).getFirstName(), "User 2 first name");
    }

    @Test
    public void testGetUserListNoResults() throws Exception {
        UserGetRequest request = new UserGetRequest();
        request.setEmail(Sets.newHashSet("noUser@mail.com"));

        assertTrue(dao.getUsers(request).isEmpty(), "User list should be empty");
    }

    @Test
    public void testGetUserById() throws Exception {
        User user = dao.getUserById(1);

        assertEquals("Test", user.getFirstName(), "First name");
        assertEquals("User", user.getLastName(), "Last name");
        assertEquals("test@mail.com", user.getEmail(), "Email");
        assertEquals(WebRole.USER, user.getWebRole(), "Web Role");
    }

    @Test
    public void testGetUserByIdNotFound() throws Exception {
        UserNotFoundException e = assertThrows(UserNotFoundException.class, () -> dao.getUserById(12));
        assertEquals("User not found for id: '12'", e.getMessage(), "Message should match");
    }
}
