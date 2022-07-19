package com.hydro.common.sql.local.service;

import java.io.File;
import java.nio.file.Files;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import com.hydro.common.sql.DatabaseConnectionBuilder;
import com.hydro.common.sql.local.dao.LocalInstanceBuilderDAO;
import com.hydro.common.util.HydroLogger;

/**
 * Local instance builder if the application-local.properties is running on a
 * local environment. It will create the appropriate schema if it doesn't
 * already exist.
 * 
 * @author Sam Butler
 * @since May 29, 2022
 */
@Component
public class LocalInstanceBuilder {

    private static final HydroLogger LOGGER = new HydroLogger(LocalInstanceBuilder.class);

    private static final String LOCAL_HOST = "localhost";

    private static final String DRIVER_CLASSNAME = "com.mysql.cj.jdbc.Driver";

    /**
     * Checks to see if the application from the application-local.properties is a
     * local application serve.
     * 
     * @return {@link Boolean} saying if it is a local serve or not.
     */
    public static boolean isLocalServe(String dbUrl) {
        return dbUrl.toLowerCase().contains(LOCAL_HOST);
    }

    /**
     * Creates a local datasource object for the users local database to be used.
     * 
     * @param source The source created.
     * @return {@link DataSource} object with the updated url.
     */
    public static DataSource create(DatabaseConnectionBuilder builder) {
        LOGGER.info("Local Database Application Initializing...");
        DriverManagerDataSource source = builder.allowPublicKeyRetrieval(true).allowMultiQueries(true)
                .buildManagerSource();
        return initLocalDatabase(source);
    }

    /**
     * Initialize the local database if needed. If the database already exists then
     * it will just create the new {@link Datasource} with the updated schema name.
     * Otherwise it will create the schema and run the migration scripts against the
     * local database.
     * 
     * @param src The source of the local database.
     * @return {@link Datasource} with the updated local instance.
     */
    private static DataSource initLocalDatabase(DriverManagerDataSource src) {
        LocalInstanceBuilderDAO dao = new LocalInstanceBuilderDAO(src);

        if (dao.doesSchemaExist("hydro_db_dev__local")) {
            src.setUrl(src.getUrl().replace("?", "/hydro_db_dev__local?"));
        } else {
            dao.createLocalSchema();
            src.setUrl(src.getUrl().replace("?", "/hydro_db_dev__local?"));
            createTables(src);
        }

        LOGGER.info("Local Database Initialized!");
        return buildLocalDataSource(src);
    }

    /**
     * Creates a new datasource for the local user instance.
     * 
     * @param source The original source.
     * @return {@link DataSource} with the updated instance.
     */
    private static DataSource buildLocalDataSource(DriverManagerDataSource source) {
        return DataSourceBuilder.create().driverClassName(DRIVER_CLASSNAME).username(source.getUsername())
                .password(source.getPassword()).url(source.getUrl()).build();
    }

    /**
     * Method that will take all the db scripts in the db/migration folder and apply
     * them to the local schema. If the script can not be run it will move onto the
     * next one without haulting the rest.
     * 
     * @param source The local {@link DriverManagerDataSource}.
     */
    private static void createTables(DriverManagerDataSource source) {
        LOGGER.info("Running scripts against local database...");
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(source);
        for (File file : new File("./src/main/resources/db/migration").listFiles()) {
            try {
                template.update(Files.readString(file.toPath()), new MapSqlParameterSource());
            } catch (Exception e) {
                LOGGER.warn("Error running SQL script '{}'", file.getName());
            }
        }
        LOGGER.info("Scripts complete!");
    }
}
