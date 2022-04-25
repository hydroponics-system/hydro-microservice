package com.hydro.common.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

/**
 * Class for storing common methods for use accross the application.
 * 
 * @author Sam Butler
 * @since April 21, 2022
 */
public class CommonUtil {

    /**
     * Format a {@link Date} object based on the given format. The default timezone
     * used will be UTC.
     * 
     * @param dt     The date to format.
     * @param format The format the date should be changed too.
     * @return {@link String} of the formatted date.
     */
    public static String formatDate(Date dt, String format) {
        return formatDate(dt, format, "UTC");
    }

    /**
     * Format a {@link Date} object based on the given format.
     * 
     * @param dt       The date to format.
     * @param format   The format the date should be changed too.
     * @param timezone The timezone to format the date as.
     * @return {@link String} of the formatted date.
     */
    public static String formatDate(Date dt, String format, String timezone) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
        return simpleDateFormat.format(dt);
    }

    /**
     * Format a {@link LocalDateTime} object based on the given format.
     * 
     * @param dt     The Local Date Time to format.
     * @param format The format the date should be changed too.
     * @return {@link String} of the formatted date.
     */
    public static String formatDate(LocalDateTime dt, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return dt.format(formatter);
    }

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
