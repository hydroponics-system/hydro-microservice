package com.hydro.factory.config;

import static com.hydro.common.util.CommonUtil.generateRandomNumber;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PreDestroy;
import javax.sql.DataSource;

import com.hydro.common.sql.DataSourceDbBuilder;
import com.hydro.factory.globals.GlobalsTest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Datasouce config for the test environment.
 * 
 * @author Sam Butler
 * @since April 25, 2022
 */
@Configuration
@EnableTransactionManagement
public class DataSourceTestConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceTestConfiguration.class);

    @Autowired
    Environment ENV;

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    private DriverManagerDataSource activeDataSource;

    /**
     * Default datasource when running test. This will get called anywhere a
     * {@link DataSource} is autowired into the class.
     * 
     * @return {@link DataSource} test object.
     */
    @Bean("dataSource")
    @Profile(value = { "test", "test-local" })
    public DataSource dataSource() {
        DataSourceDbBuilder dataSourceBuilder = DataSourceDbBuilder.create().useDefaultProperties()
                .url(getEnvironmentValue("MYSQL_TEST_URL", dbUrl))
                .allowMultiQueries(true)
                .username(getEnvironmentValue("MYSQL_TEST_USERNAME", dbUsername))
                .password(getEnvironmentValue("MYSQL_TEST_PASSWORD", dbPassword));
        activeDataSource = buildDbTables(generateTestDatasource(dataSourceBuilder));
        return activeDataSource;
    }

    /**
     * Default jdbcTemplate when running test. This will get called anywhere a
     * {@link JdbcTemplate} is autowired into the class.
     * 
     * @return {@link JdbcTemplate} test object.
     */
    @Bean("jdbcTemplate")
    @DependsOn("dataSource")
    @Profile(value = { "test", "test-local" })
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(activeDataSource);
    }

    /**
     * Method for cleaning up the database when the active bean is destroyed. This
     * will drop the schema in the active config from the database.
     */
    @PreDestroy
    public void destroy() {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(activeDataSource);
        template.update(String.format("DROP SCHEMA IF EXISTS %s", activeDataSource.getSchema()), new HashMap<>());
        LOGGER.info("Schema '{}' successfully dropped!", activeDataSource.getSchema());
    }

    /**
     * This is used to generate a test datasource to used both on a local
     * environment and production test environment based on the set active profile.
     * It will create a unique test schema to insert the data into instead of using
     * the production db.
     * 
     * @param source The active datasource to the database.
     * @return {@link DataSource} test object.
     */
    private DriverManagerDataSource generateTestDatasource(DataSourceDbBuilder builder) {
        LOGGER.info("Generating test schema...");
        String testSchema = createSchema(builder.build());
        builder.url(String.format("%s/%s", dbUrl, testSchema));
        return builder.buildManagerSource();
    }

    /**
     * Method that will create the test schema to be used. It will generate a random
     * 10 digit number to append to the db schema name to keep it unique. Once the
     * schema is created it will return the name to be set on the datasource.
     * 
     * @param source The source used to create the test database.
     * @return {@link String} of the test schema name.
     */
    private String createSchema(DataSource source) {
        String schemaName = String.format("hydro_db_test__%d", generateRandomNumber());
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(source);
        template.update(String.format("CREATE SCHEMA `%s`;", schemaName), new MapSqlParameterSource());
        LOGGER.info("Schema '{}' created successfully...", schemaName);
        return schemaName;
    }

    /**
     * Method that will take all the db scripts in the db/migration folder and apply
     * them to the test schema. If the script can not be run it will move onto the
     * next one without haulting the rest. Once the scripts are all ran, it will
     * than return the active test resource that was used.
     * 
     * @param source The test {@link DataSource}.
     * @return {@link DataSource} of the test environment being used.
     */
    private DriverManagerDataSource buildDbTables(DriverManagerDataSource source) {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(source);
        for (File file : new File("./src/main/resources/db/migration").listFiles()) {
            try {
                String content = Files.readString(file.toPath());
                LOGGER.info("Executing SQL:\n{}", content);
                template.update(content, new MapSqlParameterSource());
            } catch (Exception e) {
                LOGGER.warn("Could not run Sql script '{}' {}", file.getName(), e);
            }
        }
        return source;
    }

    /**
     * This will get the environment value for the given key. If there is no active
     * profile it will return the default value passed in. If the key does not exist
     * then it will return the default value.
     * 
     * @param key          The key to search for in the properties.
     * @param defaultValue The default value to be returned if the key can't be
     *                     found
     * @return {@link String} of the value to use.
     */
    private String getEnvironmentValue(String key, String defaultValue) {
        List<String> profiles = Arrays.asList(ENV.getActiveProfiles());
        if (profiles.size() > 0 && profiles.contains(GlobalsTest.PRODUCTION_TEST)) {
            return System.getenv().get(key);
        } else {
            return defaultValue;
        }
    }

}
