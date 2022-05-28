package com.hydro.common.util;

/**
 * Class for storing common methods for use accross the application.
 * 
 * @author Sam Butler
 * @since April 21, 2022
 */
public class CommonUtil {

    /**
     * Method that will simply generate a random 10 digit number based on the local
     * time.
     * 
     * @return {@link Long} of the random number.
     */
    public static long generateRandomNumber() {
        return (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
    }
}
