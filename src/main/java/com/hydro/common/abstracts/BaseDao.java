package com.hydro.common.abstracts;

import javax.sql.DataSource;

import com.opengamma.elsql.ElSqlConfig;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * Base DAO class containing the shared methods between daos.
 * 
 * @author Sam Butler
 * @since February 2, 2022
 */
public class BaseDao extends AbstractSqlDao {

    public BaseDao(DataSource source) {
        super(new NamedParameterJdbcTemplate(source), ElSqlConfig.MYSQL);
    }
}
