package com.hydro.common.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CommonUtilTest {

    @Test
    public void formatDateShouldFormatDatetoString() {
        Date testDate = new Date(100000);
        assertEquals("01-01-1970 00:01:40", CommonUtil.formatDate(testDate, "MM-dd-yyyy HH:mm:ss"));
    }

    @Test
    public void formatLocalDateShouldFormatDatetoString() {
        LocalDateTime testDate = LocalDateTime.of(2012, 2, 12, 10, 22, 15);
        assertEquals("02-12-2012 10:22:15", CommonUtil.formatDate(testDate, "MM-dd-yyyy HH:mm:ss"));
    }
}
