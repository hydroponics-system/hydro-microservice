package com.hydro.jwt.utility;

import static com.hydro.factory.data.UserFactoryData.userData;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import com.hydro.ActiveProfile;
import com.hydro.app.user.client.domain.enums.WebRole;
import com.hydro.common.util.HydroLogger;
import com.hydro.factory.annotations.HydroServiceTest;

@HydroServiceTest
public class JwtHolderTest {

    @Mock
    private ActiveProfile profile;

    @Mock
    private HydroLogger hydroLogger;

    private JwtHolder jwtHolder;

    private String token;

    @BeforeEach
    public void setup() throws Exception {
        when(profile.getSigningKey()).thenReturn("TEST-KEY");

        JwtTokenUtil tokenUtil = new JwtTokenUtil(profile);

        jwtHolder = new JwtHolder(profile, hydroLogger);
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
