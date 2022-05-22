package com.hydro.common.sql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.sql.DataSource;

import com.hydro.factory.annotations.HydroTest;

import org.junit.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@HydroTest
public class DatabaseConnectionBuilderTest {

    @Test
    public void testDefaultDataSourceCreate() {
        DataSource source = DatabaseConnectionBuilder.create().build();
        assertNotNull(source);
    }

    @Test
    public void testDefaultDriverManagerDataSourceCreate() {
        DriverManagerDataSource source = DatabaseConnectionBuilder.create().buildManagerSource();
        assertNotNull(source);
        assertEquals("", source.getUrl(), "Url should be set but empty");
    }

    @Test
    public void testDefaultPropertiesAreAddedToUrl() {
        DriverManagerDataSource source = DatabaseConnectionBuilder.create().url("fakeURL").useDefaultProperties()
                .buildManagerSource();
        assertNotNull(source);
        assertEquals(
                "fakeURL?useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&useUnicode=true&serverTimezone=UTC",
                source.getUrl(), "Default properties get added to url");
    }
}
