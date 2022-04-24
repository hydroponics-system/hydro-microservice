package com.hydro.common.abstracts;

import org.springframework.jdbc.core.RowMapper;

/**
 * Base mapper class for mappers
 * 
 * @author Sam Butler
 * @since April 21, 2022
 */
public abstract class AbstractMapper<T> extends AbstractSqlGlobals implements RowMapper<T> {

}
