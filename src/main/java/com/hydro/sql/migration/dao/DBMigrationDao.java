package com.hydro.sql.migration.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Class for handling the dao calls db migration
 * 
 * @author Sam Butler
 * @since October 9, 2021
 */
@Repository
public class DBMigrationDao {

    @Autowired
    private NamedParameterJdbcTemplate template;

    /**
     * Will execute the given sql script against the database.
     * 
     * @param script The script to be run against the database.
     */
    public void executeScript(String script) {
        template.update(script, new MapSqlParameterSource());
    }
}
