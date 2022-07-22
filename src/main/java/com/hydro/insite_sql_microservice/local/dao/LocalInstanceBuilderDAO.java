package com.hydro.insite_sql_microservice.local.dao;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.hydro.insite_common_microservice.abstracts.BaseDao;

/**
 * Local instance builder dao for interacting with the users local database.
 * 
 * @author Sam Butler
 * @since May 29, 2022
 */
@Repository
public class LocalInstanceBuilderDAO extends BaseDao {

    public LocalInstanceBuilderDAO(DataSource source) {
        super(source);
    }

    /**
     * Creates a local instance of the hydro_db_dev schema on the users local
     * database.
     */
    public void createLocalSchema() {
        execute(getSql("createLocalSchema"));
    }

    /**
     * Checks to see if the passed in schema exists for the given name.
     * 
     * @param name The name of schema to filter on.
     * @return {@link Boolean} determining if the schema exists or not.
     */
    public boolean doesSchemaExist(String name) {
        var params = parameterSource("schemaName", name);
        try {
            return !get(getSql("doesSchemaExist", params), params, String.class).isBlank();
        } catch (Exception e) {
            return false;
        }
    }
}
