package com.hydro.common.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.hydro.factory.annotations.HydroTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@HydroTest
public class CommonUtilTest {

    @Test
    public void testGenerateRandomNumber() {
        Long value = CommonUtil.generateRandomNumber();
        assertNotNull(value);
        assertTrue(value.toString().length() == 10, "Length of random number should be 10");
    }

    @ParameterizedTest
    @ValueSource(ints = { 1, 2, 3, 4, 5, 6, 7, 8, 9 })
    public void testGenerateRandomNumberWithLength(int number) {
        Long value = CommonUtil.generateRandomNumber(number);
        assertNotNull(value);
        assertTrue(value.toString().length() == number, "Length of random number should be " + number);
    }

    @ParameterizedTest
    @ValueSource(ints = { 100, 0, 15, -1 })
    public void testGenerateRandomNumberWithInvalidLength(int number) {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> CommonUtil.generateRandomNumber(number));
        assertEquals("Length must be between 0 and 10", e.getMessage(), "Exception Message");
    }
}
