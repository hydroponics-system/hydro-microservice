package com.hydro.common.sql;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

/**
 * Datasource builder class for managing and building a datasource instance with
 * the database.
 * 
 * @author Sam Butler
 * @since May 21, 2022
 */
@Service
public class DatasourceDbBuilder {

    private DriverManagerDataSource source;

    private String dbProperties;

    /**
     * Private constructor for setting the datasource.
     * 
     * @param s The datasource to be set.
     */
    private DatasourceDbBuilder(DriverManagerDataSource s) {
        this.source = s;
        this.source.setDriverClassName("com.mysql.cj.jdbc.Driver");
    }

    /**
     * Begins the creation and new instance of the datasource.
     * 
     * @return {@link DatasourceDbBuilder} instance with the set datasource.
     */
    public static DatasourceDbBuilder create() {
        return new DatasourceDbBuilder(new DriverManagerDataSource());
    }

    /**
     * Begins the creation and the passed in datasource instance.
     * 
     * @param s The datasource to be set.
     * @return {@link DatasourceDbBuilder} instance with the set datasource.
     */
    public static DatasourceDbBuilder create(DriverManagerDataSource s) {
        return new DatasourceDbBuilder(s);
    }

    // public static DatasourceDbBuilder
}
