package com.hydro.common.util;

import io.jsonwebtoken.lang.Assert;

/**
 * Class for storing common methods for use accross the application.
 * 
 * @author Sam Butler
 * @since April 21, 2022
 */
public class CommonUtil {

    /**
     * Method that will simply generate a random 8 digit number based on the local
     * time.
     * 
     * @return {@link Long} of the random number.
     */
    public static long generateRandomNumber() {
        return generateRandomNumber(10);
    }

    /**
     * Method that will simply generate a random number based on the given length
     * that is desired.
     * 
     * @param length The length of the random number.
     * @return {@link Long} of the random number.
     */
    public static long generateRandomNumber(int length) {
        Assert.isTrue(length > 0 && length < 11, "Length must be between 0 and 10");

        long numberThreshold = Long.parseLong("9" + "0".repeat(length - 1));
        long mask = Long.parseLong("1" + "0".repeat(length - 1));
        return (long) Math.floor(Math.random() * numberThreshold) + mask;
    }
}
