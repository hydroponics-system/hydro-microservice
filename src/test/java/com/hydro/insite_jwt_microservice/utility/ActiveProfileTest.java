package com.hydro.insite_jwt_microservice.utility;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import com.hydro.factory.annotations.HydroServiceTest;

@HydroServiceTest
public class ActiveProfileTest {

    @InjectMocks
    private ActiveProfile profile;

    @Test
    public void testsetPropertyFile() {
        profile.setEnvironmentProperties();
        assertEquals("local", System.getProperty("spring.profiles.active"));
    }

    @Test
    public void testGetPropertyFilePath() {
        assertEquals("src/main/resources/application-local.properties", profile.getPropertyFilePath());
    }

    @Test
    public void testGetAppPropertiesName() {
        assertEquals("application-local.properties", profile.getAppPropertiesName());
    }

    @Test
    public void testGetEnvironmentUrl() {
        assertEquals("src/main", profile.getEnvironmentUrl());
    }

    @Test
    public void testGetSigningKey() {
        assertEquals("local-key", profile.getSigningKey());
    }
}
