package com.hydro.sql.stack.service;

import java.io.File;
import java.nio.file.Files;
import java.util.Date;

import javax.sql.DataSource;

import com.hydro.ActiveProfile;
import com.hydro.common.enums.Environment;
import com.hydro.common.exceptions.BaseException;
import com.hydro.sql.stack.dao.SqlStackDao;
import com.hydro.sql.stack.domain.DatabaseStack;
import com.hydro.sql.stack.domain.enums.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.lang.Assert;

/**
 * Service for managing the db migration of scripts.
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Service
public class SqlStackService {

    private final String DB_URL_PROPERTIES = "?useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&allowMultiQueries=true&serverTimezone=UTC";

    private final String SCRIPT_LOCATION = "./src/main/resources/db/migration";

    private final Logger LOGGER = LoggerFactory.getLogger(SqlStackService.class);

    @Autowired
    private SqlStackDao dao;

    @Autowired
    private ActiveProfile activeProfile;

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    /**
     * Method for creating a stack for the given database user.
     * 
     * @param dbUsername The db user name.
     */
    public DatabaseStack createStack(String dbUsername) {
        Assert.notNull(dbUsername, "Username can not be null");
        DatabaseStack updatedStackInfo = new DatabaseStack();

        if (dao.doesDBUserExist(dbUsername)) {
            updatedStackInfo = generateStackName(dbUsername);

            LOGGER.info("Starting Stack creation with name '{}'", updatedStackInfo.getStackName());
            dao.dropStack(updatedStackInfo.getStackName());
            dao.createStack(updatedStackInfo.getStackName());

            DataSource source = generateDataSourceForNewStack(updatedStackInfo.getStackName());

            insertTables(new NamedParameterJdbcTemplate(source));
            insertStackEnvironmentData(source, updatedStackInfo);
            grantAccessToUserOnStack(updatedStackInfo.getStackName(), dbUsername);
            LOGGER.info("Stack '{}' successfully created!", updatedStackInfo.getStackName());
        } else {
            throw new BaseException(String.format("Database User '%s' does not exist!", dbUsername));
        }
        return updatedStackInfo;
    }

    /**
     * Method for dropping a stack.
     * 
     * @param stackName The stack name to drop.
     */
    public void dropStack(String stackName) {
        Assert.notNull(stackName, "Stack name can not be null");

        if (!dao.doesStackExist(stackName))
            throw new BaseException(String.format("The stack '%s' does not exist!", stackName));
        LOGGER.info("Dropping Stack '{}'...", stackName);
        dao.dropStack(stackName);
        LOGGER.info("Stack '{}' successfully dropped!", stackName);
    }

    /**
     * Method for granting access for a user to a stack.
     * 
     * @param stackName  The name of the stack to grant access too.
     * @param dbUsername The db user name.
     */
    public void grantAccessToUserOnStack(String stackName, String dbUsername) {
        Assert.notNull(dbUsername, "Username can not be null");
        Assert.notNull(stackName, "Stack name can not be null");

        if (!dao.doesStackExist(stackName))
            throw new BaseException(String.format("The stack '%s' does not exist!", stackName));

        if (!dao.doesDBUserExist(dbUsername))
            throw new BaseException(String.format("Database User '%s' does not exist!", dbUsername));

        LOGGER.info("Granting access for user '{}' to stack '{}'", dbUsername, stackName);
        dao.grantUserAccessToStack(dbUsername, stackName);
        dao.flushPrivileges();
    }

    /**
     * Method that will take in a stack name and database user that needs to be
     * revoked.
     * 
     * @param stackName  The name of the stack to revoke access too.
     * @param dbUsername The db user name.
     */
    public void revokeAccessToUserOnStack(String stackName, String dbUsername) {
        Assert.notNull(dbUsername, "Username can not be null");
        Assert.notNull(stackName, "Stack name can not be null");

        if (!dao.doesStackExist(stackName))
            throw new BaseException(String.format("The stack '%s' does not exist!", stackName));

        if (!dao.doesDBUserExist(dbUsername))
            throw new BaseException(String.format("Database User '%s' does not exist!", dbUsername));

        LOGGER.info("Revoking access for user '{}' to stack '{}'", dbUsername, stackName);
        dao.revokeUserAccessToStack(dbUsername, stackName);
        dao.flushPrivileges();
    }

    /**
     * Generates a db stack based off the db user name;
     * 
     * @param dbUsername The username of the user in the database
     * @return {@link DatabaseStack} of the updated information.
     */
    private DatabaseStack generateStackName(String dbUsername) {
        DatabaseStack stack = new DatabaseStack();
        Stack type = activeProfile.getEnvironment().equals(Environment.PRODUCTION) ? Stack.PROD : Stack.DEV;
        String name = String.format("Stack_%s__%s", dbUsername, type.toString());

        stack.setUsername(dbUsername);
        stack.setStackName(name);
        stack.setStackType(type);
        stack.setCreatedDate(new Date());
        return stack;
    }

    /**
     * Inserts data into the the stack from it's corresponding environment.
     * 
     * @param source The source of the stack.
     */
    public void insertStackEnvironmentData(DataSource source, DatabaseStack stack) {
        LOGGER.info("Inserting data into tables on new stack...");

        SqlStackDao stackDao = new SqlStackDao(source);
        for (String table : stackDao.getTablesByStackName(stack.getStackName())) {
            try {
                stackDao.copyTableDataFromEnvironment(table, stack.getStackType());
            } catch (Exception e) {
                // Do Nothing
            }
        }
        LOGGER.info("Table Data insert complete!");
    }

    /**
     * Inserts the tables from the resources into the new stack.
     * 
     * @param template The template to run the script against.
     */
    public void insertTables(NamedParameterJdbcTemplate template) {
        LOGGER.info("Inserting tables into stack...");

        for (File file : new File(SCRIPT_LOCATION).listFiles()) {
            executeScript(template, file);
        }
        LOGGER.info("Table insert complete!");
    }

    /**
     * Helper method that will run the content in the passed in file as a sql script
     * against the database.
     * 
     * @param f The file to pull the content from.
     */
    private void executeScript(NamedParameterJdbcTemplate template, File f) {
        try {
            String content = Files.readString(f.toPath());
            template.update(content, new MapSqlParameterSource());
        } catch (Exception e) {
            LOGGER.warn("Error running SQL script '{}'", f.getName());
        }
    }

    /**
     * Generates a temp {@link NamedParameterJdbcTemplate} for the new stack so
     * tables and data can be inserted into it.
     * 
     * @param stackName The name of the stack to authenticate for.
     * @return {@link NamedParameterJdbcTemplate} instance
     */
    private DataSource generateDataSourceForNewStack(String stackName) {
        Stack stackType = activeProfile.getEnvironment().equals(Environment.PRODUCTION) ? Stack.PROD : Stack.DEV;

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl(dbUrl.replace(stackType.getSchema(), stackName) + DB_URL_PROPERTIES);
        dataSource.setUsername(dbUsername);
        dataSource.setPassword(dbPassword);
        return dataSource;
    }

}
