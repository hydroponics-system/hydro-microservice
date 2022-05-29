package com.hydro.common.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.hydro.factory.annotations.HydroTest;

import org.junit.jupiter.api.Test;

@HydroTest
public class CommonUtilTest {

    @Test
    public void testGenerateRandomNumber() {
        Long value = CommonUtil.generateRandomNumber();
        assertNotNull(value);
        assertTrue(value.toString().length() == 10, "Length of random number should be 10");
    }

    @Test
    public void testGenerateRandomNumberWithLength() {
        Long value = CommonUtil.generateRandomNumber(6);
        assertNotNull(value);
        assertTrue(value.toString().length() == 6, "Length of random number should be 6");
    }

    @Test
    public void testGenerateRandomNumberWithInvalidLength() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> CommonUtil.generateRandomNumber(100));
        assertEquals("Length must be between 0 and 10", e.getMessage(), "Exception Message");
    }
}
