package com.hydro.app.system.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import com.hydro.app.system.client.domain.HydroSystem;
import com.hydro.app.system.client.domain.request.HydroSystemGetRequest;
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
        List<HydroSystem> systems = dao.getSystems(new HydroSystemGetRequest());

        assertEquals(3, systems.size(), "List size should be 3");
    }
}
