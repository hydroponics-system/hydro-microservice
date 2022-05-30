package com.hydro.app.system.dao;

import com.hydro.factory.annotations.HydroDaoTest;
import com.hydro.factory.config.test.HydroSystemDAOTestConfig;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

@ContextConfiguration(classes = HydroSystemDAOTestConfig.class)
@Sql("/scripts/system/hydrosystemDAO/init.sql")
@HydroDaoTest
public class HydroSystemDAOTest {

    @Autowired
    private HydroSystemDAO dao;

    @Test
    public void testGetSystemsEmptyRequest() {

    }
}
