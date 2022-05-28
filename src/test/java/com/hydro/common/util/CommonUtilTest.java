package com.hydro.common.util;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.hydro.factory.annotations.HydroTest;

import org.junit.jupiter.api.Test;

@HydroTest
public class CommonUtilTest {

    @Test
    public void testGenerateRandomNumber() {
        Long value = CommonUtil.generateRandomNumber();
        assertNotNull(value);
    }
}
