package com.hydro.factory.config;

import java.io.File;
import java.nio.file.Files;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class DataSourceTestConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceTestConfig.class);

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    @Bean
    @Profile(value = { "test", "test-local" })
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(dbUsername);
        dataSource.setPassword(dbPassword);

        LOGGER.info("Logging in with user '{}'", dbUsername);

        DataSource testDataSource = generateTestSchema(dataSource);
        return buildDbTables(testDataSource);
    }

    private DataSource generateTestSchema(DriverManagerDataSource source) {
        LOGGER.info("Generating test schema...");
        String testSchema = createSchema(source);
        source.setUrl(dbUrl.replace("?", String.format("/%s?", testSchema)));
        source.setSchema(testSchema);
        return source;
    }

    private String createSchema(DataSource source) {
        String schemaName = String.format("hydro_db_test__%d",
                (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(source);
        template.update(String.format("CREATE SCHEMA `%s`;", schemaName), new MapSqlParameterSource());
        LOGGER.info("Schema '{}' created successfully...", schemaName);
        return schemaName;
    }

    private DataSource buildDbTables(DataSource source) {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(source);
        File[] files = new File("./src/main/resources/db").listFiles();
        for (File file : files) {
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

}
