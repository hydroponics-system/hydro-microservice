package com.hydro.common.sql;

import static com.hydro.common.datetime.DateTimeMapper.printDate;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.hydro.common.enums.TextEnum;

/**
 * Sql builder to create all query binding parameters for making querys to the
 * database.
 * 
 * @author Sam Butler
 * @since Februrary 2, 2022
 */
public class SqlParamBuilder {
    private MapSqlParameterSource sqlParams;

    private SqlParamBuilder(MapSqlParameterSource sqlParams) {
        if (sqlParams == null) {
            this.sqlParams = new MapSqlParameterSource();
        } else {
            this.sqlParams = sqlParams;
        }
    }

    /**
     * Initialize the {@link SqlParamBuilder}.
     * 
     * @return {@link SqlParamBuilder} for an empty object.
     */
    public static SqlParamBuilder with() {
        return new SqlParamBuilder(null);
    }

    /**
     * Add a parameter to the sql map.
     * 
     * @param name  The name of the parameter.
     * @param value The value of the parameter.
     * @return this builder object {@link SqlParamBuilder}
     */
    public SqlParamBuilder withParam(String name, Object value) {
        this.sqlParams.addValue(name, value);
        return this;
    }

    /**
     * Add parameter to sql map and check that the text enum is not null, if not get
     * the text id.
     * 
     * @param name  The name of the parameter.
     * @param value The value of the parameter
     * @return this builder object {@link SqlParamBuilder}
     */
    public SqlParamBuilder withParam(String name, TextEnum value) throws Exception {
        return withParam(name, value == null ? null : value.getTextId());
    }

    /**
     * Add parameter to sql map for the given {@link Boolean} value.
     * 
     * @param name  The name of the parameter.
     * @param value The value of the parameter
     * @return this builder object {@link SqlParamBuilder}
     */
    public SqlParamBuilder withParam(String name, Boolean value) {
        return withParam(name, value == null ? null : value ? 1 : 0);
    }

    /**
     * Add parameter to sql map for the given {@link LocalDateTime} object.
     * 
     * @param name  The name of the parameter.
     * @param value The value of the parameter
     * @return this builder object {@link SqlParamBuilder}
     */
    public SqlParamBuilder withParam(String name, LocalDateTime value) {
        return withParam(name, printDate(value));
    }

    /**
     * Add parameter to sql map and check that the text enum is not null, if not get
     * the text id.
     * 
     * @param name  The name of the parameter.
     * @param value The value of the parameter
     * @return this builder object {@link SqlParamBuilder}
     */
    public SqlParamBuilder withParam(String name, Date value) {
        return withParam(name, printDate(value));
    }

    /**
     * Add parameter to sql map for an enum collection and check that the text enum
     * is not null, if not get the text id.
     * 
     * @param name  The name of the parameter.
     * @param value The value of the parameter
     * @return this builder object {@link SqlParamBuilder}
     */
    public <T> SqlParamBuilder withParamTextEnumCollection(String name, Collection<? extends TextEnum> values) {
        return withParam(name,
                values == null ? null : values.stream().map(TextEnum::getTextId).collect(Collectors.toList()));
    }

    /**
     * Retrieve {@link MapSqlParameterSource} object for the given builder.
     * 
     * @return {@link MapSqlParameterSource}
     */
    public MapSqlParameterSource build() {
        return sqlParams;
    }
}
