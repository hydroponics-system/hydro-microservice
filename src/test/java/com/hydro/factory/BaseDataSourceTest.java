package com.hydro.factory;

import java.util.HashMap;

import javax.annotation.PostConstruct;

import com.hydro.factory.config.DataSourceTestConfig;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Base Datasource class for the dao test.
 * 
 * @author Sam Butler
 * @since April 25, 2022
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { DataSourceTestConfig.class })
@ActiveProfiles("test")
public abstract class BaseDataSourceTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseDataSourceTest.class);

    private static DriverManagerDataSource staticSource;

    @Autowired
    private DriverManagerDataSource source;

    @PostConstruct
    public void init() {
        BaseDataSourceTest.staticSource = source;
    }

    @AfterAll
    public static void cleanUp() {
        LOGGER.info("Dropping test schema...");
        // dropSchema();

    }

    /**
     * This will drop the test schema that was created for the datasource.
     */
    private static void dropSchema() {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(staticSource);
        template.update(String.format("DROP SCHEMA IF EXISTS %s", staticSource.getSchema()), new HashMap<>());
        LOGGER.info("Schema '{}' successfully dropped!", staticSource.getSchema());
    }
}
