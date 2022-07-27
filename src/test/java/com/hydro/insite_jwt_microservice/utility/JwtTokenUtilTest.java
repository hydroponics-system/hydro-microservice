package com.hydro.insite_jwt_microservice.utility;

import static com.hydro.factory.data.UserFactoryData.userData;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import com.hydro.factory.annotations.HydroServiceTest;
import com.hydro.insite_common_microservice.enums.Environment;
import com.hydro.insite_user_microservice.client.domain.enums.WebRole;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@HydroServiceTest
public class JwtTokenUtilTest {

    @InjectMocks
    private JwtTokenUtil jwtTokenUtil;

    @Test
    public void testGenerateToken() {
        String token = jwtTokenUtil.generateToken(userData());

        assertNotNull(token, "Token should not be null");
        assertEquals(2, token.chars().filter(c -> c == '.').count(), "Confirm token contains two periods");
    }

    @Test
    public void testGetAllClaimsFromToken() {
        Claims claims = jwtTokenUtil.getAllClaimsFromToken(jwtTokenUtil.generateToken(userData()));

        assertEquals(12, claims.get("userId"), "User Id");
        assertEquals("Test", claims.get("firstName"), "User Id");
        assertEquals("User", claims.get("lastName"), "User Id");
        assertEquals("test@user.com", claims.get("email"), "User Id");
        assertEquals(WebRole.ADMIN.toString(), claims.get("webRole"), "User Id");
        assertEquals(Environment.LOCAL.toString(), claims.get("env"), "User Id");
        assertEquals(false, claims.get("passwordReset"), "User Id");
    }

    @Test
    public void testIsTokenExpiredValid() {
        String token = jwtTokenUtil.generateToken(userData());
        assertFalse(jwtTokenUtil.isTokenExpired(token), "Token not expired");
    }

    @Test
    public void testIsTokenExpiredInvalid() {
        String expiredToken = Jwts.builder().setClaims(new HashMap<>())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() - 18000000))
                .signWith(SignatureAlgorithm.HS512, "local-key").compact();
        assertTrue(jwtTokenUtil.isTokenExpired(expiredToken), "Token expired");
    }

    @Test
    public void testGetExpirationDateFromToken() {
        String token = Jwts.builder().setClaims(new HashMap<>()).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(180000000)).signWith(SignatureAlgorithm.HS512, "local-key").compact();
        LocalDateTime tokenDate = jwtTokenUtil.getExpirationDateFromToken(token);

        assertNotNull(tokenDate, "Token Expiration Time");
    }
}
