package com.hydro.insite_common_microservice.datetime;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

/**
 * Utility class containing static methods for mapping DateTime objects to/from
 * String values for use in DAOs. It is expected that these methods will be
 * statically imported if using Local Date Time objects.
 * 
 * @author Sam Butler
 * @since May 27, 2022
 */
public class DateTimeMapper {
    private static final String DATE_TIME_PRINT = "yyyy-MM-dd HH:mm:ss";

    /**
     * Format a {@link Date} object based on the given format. The default timezone
     * used will be UTC.
     * 
     * @param dt     The date to format.
     * @param format The format the date should be changed too.
     * @return {@link String} of the formatted date.
     */
    public static String printDate(Date dt, String format) {
        return printDate(dt, format, "UTC");
    }

    /**
     * Format a {@link Date} object based on the given format.
     * 
     * @param dt       The date to format.
     * @param format   The format the date should be changed too.
     * @param timezone The timezone to format the date as.
     * @return {@link String} of the formatted date.
     */
    public static String printDate(Date dt, String format, String timezone) {
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
    public static String printDate(LocalDateTime dt, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return dt.format(formatter);
    }

    /**
     * Formats the date into the default format for sql. If the passed in date is
     * null then the method will return null
     * 
     * @param dt The date to format.
     * @return {@link String} of the formatted date.
     */
    public static String printDate(LocalDateTime dt) {
        if (dt == null) {
            return null;
        }
        return printDate(dt, DATE_TIME_PRINT);
    }

    /**
     * Formats the date into the default format for sql. If the passed in date is
     * null then the method will return null
     * 
     * @param dt The date to format.
     * @return {@link String} of the formatted date.
     */
    public static String printDate(Date dt) {
        if (dt == null) {
            return null;
        }
        return printDate(dt, DATE_TIME_PRINT);
    }
}
