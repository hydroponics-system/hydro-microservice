package com.hydro.insite_jwt_microservice.utility;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import com.hydro.factory.annotations.HydroServiceTest;
import com.hydro.insite_common_microservice.enums.Environment;

@HydroServiceTest
public class ActiveProfileTest {

    @InjectMocks
    private ActiveProfile profile;

    // @Test
    // public void testSetPropertyFile() {
    // profile.setEnvironmentProperties();
    // assertEquals("local", System.getProperty("spring.profiles.active"));
    // }

    @Test
    public void testGetEnvironment() {
        System.setProperty("spring.profiles.active", "local");
        assertEquals(Environment.LOCAL, profile.getEnvironment());
        System.clearProperty("spring.profiles.active");
    }

    @Test
    public void testGetSigningKey() {
        assertEquals("local-key", profile.getSigningKey());
    }
}
