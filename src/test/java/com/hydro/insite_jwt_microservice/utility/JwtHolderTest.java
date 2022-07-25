package com.hydro.insite_jwt_microservice.utility;

import static com.hydro.factory.data.UserFactoryData.userData;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.hydro.factory.annotations.HydroServiceTest;
import com.hydro.insite_common_microservice.util.HydroLogger;
import com.hydro.insite_user_microservice.client.domain.enums.WebRole;

@HydroServiceTest
public class JwtHolderTest {

    @Mock
    private HydroLogger hydroLogger;

    @Mock
    private JwtEnvironment jwtEnvironment;

    @InjectMocks
    private JwtHolder jwtHolder;

    private String token;

    @BeforeEach
    public void setup() throws Exception {
        JwtTokenUtil tokenUtil = new JwtTokenUtil();
        token = tokenUtil.generateToken(userData());
    }

    @Test
    public void testGetUserId() {
        assertEquals(12, jwtHolder.getUserId(token));
    }

    @Test
    public void testGetUserEmail() {
        assertEquals("test@user.com", jwtHolder.getEmail(token));
    }

    @Test
    public void testGetUserResetPassword() {
        assertEquals(false, jwtHolder.getResetPassword(token));
    }

    @Test
    public void testGetUserWebRole() {
        assertEquals(WebRole.ADMIN, jwtHolder.getWebRole(token));
    }

    @Test
    public void testParseTokenERROR() {
        Object data = jwtHolder.parse("INVALID", null);
        verify(hydroLogger).warn("Could not parse key '{}' from token.", "INVALID");
        assertNull(data, "Returned data should be null");
    }
}
