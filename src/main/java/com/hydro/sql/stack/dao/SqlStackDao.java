package com.hydro.sql.stack.dao;

import java.util.List;

import javax.sql.DataSource;

import com.hydro.common.abstracts.BaseDao;
import com.hydro.sql.stack.domain.enums.Stack;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

/**
 * Class for handling the dao calls db migration
 * 
 * @author Sam Butler
 * @since October 9, 2021
 */
@Repository
public class SqlStackDao extends BaseDao {

    public SqlStackDao(DataSource source) {
        super(source);
    }

    /**
     * Checks to see if the passed in username exists as a database user.
     * 
     * @param username The username to validate.
     * @return {@link Boolean} if the user exists or not.
     */
    public boolean doesDBUserExist(String username) {
        MapSqlParameterSource params = parameterSource("user", username);
        try {
            String user = getTemplate().queryForObject(getSql("findDBUser", params), params, String.class);
            return !"".equals(user);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks to see if the passed in stack name exists.
     * 
     * @param stackName The stack name to search for.
     * @return {@link Boolean} of the stack status.
     */
    public boolean doesStackExist(String stackName) {
        MapSqlParameterSource params = parameterSource("stackName", stackName);
        try {
            String stack = getTemplate().queryForObject(getSql("findStack"), params, String.class);
            return !"".equals(stack);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * This will create a schema stack on the database.
     * 
     * @param stackName The stack name to create.
     */
    public void createStack(String stackName) {
        getTemplate().update(getSql("createStack").replace(":stack", stackName), new MapSqlParameterSource());
    }

    /**
     * This will drop a schema stack on the database.
     * 
     * @param stackName The stack name to drop.
     */
    public void dropStack(String stackName) {
        getTemplate().update(getSql("dropStack").replace(":stack", stackName), new MapSqlParameterSource());
    }

    /**
     * Get a list of tables from the current schema stack.
     * 
     * @return {@link List} of table names.
     */
    public List<String> getTablesByStackName(String stackName) {
        MapSqlParameterSource params = parameterSource("stackName", stackName);
        return getTemplate().queryForList(getSql("getStackTables", params), params, String.class);
    }

    /**
     * Copy the data from the environment table to the current stack table.
     * 
     * @param destinationTable Where the data should go.
     * @param st               The stack of the current environment.
     */
    public void copyTableDataFromEnvironment(String destinationTable, Stack st) {
        String query = getSql("copyTableDataFromEnvironment").replace(":destTable", destinationTable);
        query = query.replace(":envTable", String.format("%s.%s", st.getSchema(), destinationTable));
        getTemplate().update(query, new MapSqlParameterSource());
    }

    /**
     * Grants access to the given username on the specified stack.
     * 
     * @param username  The user to grant access for.
     * @param stackName The stack to grant access too.
     */
    public void grantUserAccessToStack(String username, String stackName) {
        String query = getSql("grantAccessToStack").replace(":stackName", stackName.replace("_", "\\_"));
        query = query.replace(":username", username);
        getTemplate().update(query, new MapSqlParameterSource());
    }

    /**
     * Revokes access to the given username on the specified stack.
     * 
     * @param username  The user to revoke access for.
     * @param stackName The stack to revoke access too.
     */
    public void revokeUserAccessToStack(String username, String stackName) {
        String query = getSql("revokeAccessToStack").replace(":stackName", stackName.replace("_", "\\_"));
        query = query.replace(":username", username);
        getTemplate().update(query, new MapSqlParameterSource());
    }

    /**
     * Flushes the user privileges for the stack
     */
    public void flushPrivileges() {
        getTemplate().update(getSql("flushPrivileges"), new MapSqlParameterSource());
    }
}
