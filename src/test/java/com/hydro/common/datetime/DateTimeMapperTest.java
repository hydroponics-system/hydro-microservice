package com.hydro.common.datetime;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.Date;

import com.hydro.factory.annotations.HydroTest;

import org.junit.jupiter.api.Test;

@HydroTest
public class DateTimeMapperTest {
    @Test
    public void testFormatDateShouldFormatDatetoString() {
        Date testDate = new Date(100000);
        assertEquals("01-01-1970 00:01:40", DateTimeMapper.printDate(testDate, "MM-dd-yyyy HH:mm:ss"));
    }

    @Test
    public void testFormatLocalDateShouldFormatDatetoString() {
        LocalDateTime testDate = LocalDateTime.of(2012, 2, 12, 10, 22, 15);
        assertEquals("02-12-2012 10:22:15", DateTimeMapper.printDate(testDate, "MM-dd-yyyy HH:mm:ss"));
    }

    @Test
    public void testPrintDateDefaultSqlFormat() {
        LocalDateTime testDate = LocalDateTime.of(2012, 2, 12, 10, 22, 15);
        assertEquals("2012-02-12 10:22:15", DateTimeMapper.printDate(testDate));
    }
}
