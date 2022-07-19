package com.hydro.common.util;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;

import com.hydro.factory.annotations.HydroServiceTest;

@HydroServiceTest
public class HydroLoggerTest {
    @Mock
    private Logger LOGGER;

    @InjectMocks
    private HydroLogger hydroLogger;

    @Test
    public void testINFOLog() {
        hydroLogger.info("Test INFO Message");
        verify(LOGGER).info("Test INFO Message");
    }

    @Test
    public void testINFOLogParams() {
        hydroLogger.info("Test INFO Message params {} {} {}", "value", "many", "test");
        verify(LOGGER).info("Test INFO Message params {} {} {}", "value", "many", "test");
    }

    @Test
    public void testWARNLog() {
        hydroLogger.warn("Test INFO Message");
        verify(LOGGER).warn("Test INFO Message");
    }

    @Test
    public void testWARNLogParams() {
        hydroLogger.warn("Test INFO Message params {} {} {}", "value", "many", "test");
        verify(LOGGER).warn("Test INFO Message params {} {} {}", "value", "many", "test");
    }

    @Test
    public void testERRORLog() {
        hydroLogger.error("Test INFO Message");
        verify(LOGGER).error("Test INFO Message");
    }

    @Test
    public void testERRORLogParams() {
        hydroLogger.error("Test INFO Message params {} {} {}", "value", "many", "test");
        verify(LOGGER).error("Test INFO Message params {} {} {}", "value", "many", "test");
    }
}
