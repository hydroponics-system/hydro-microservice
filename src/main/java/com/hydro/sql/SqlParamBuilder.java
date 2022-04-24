package com.hydro.sql;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.hydro.common.enums.TextEnum;
import com.hydro.common.search.CommonParam;
import com.hydro.common.search.SearchField;
import com.hydro.common.search.SearchFieldParams;
import com.hydro.common.search.SearchParam;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

/**
 * Sql builder to create all query binding parameters for making querys to the
 * database.
 * 
 * @author Sam Butler
 * @since Februrary 2, 2022
 */
public class SqlParamBuilder {
    private MapSqlParameterSource sqlParams;
    private CommonParam commonParam;

    private SqlParamBuilder(CommonParam commonParam, MapSqlParameterSource sqlParams) {
        this.commonParam = commonParam;
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
        return new SqlParamBuilder(null, null);
    }

    /**
     * Initialize the {@link SqlParamBuilder} with no starting params.
     * 
     * @return {@link SqlParamBuilder} for an empty object.
     */
    public static SqlParamBuilder with(CommonParam commonParam) {
        return new SqlParamBuilder(commonParam, null);
    }

    /**
     * Initialize the {@link SqlParamBuilder} with the given starting params.
     * 
     * @param params {@link MapSqlParameterSource} used for adding extra parameters.
     * @return {@link SqlParamBuilder} with the starting parameters
     */
    public static SqlParamBuilder with(CommonParam commonParam, MapSqlParameterSource params) {
        return new SqlParamBuilder(commonParam, params);
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
    public SqlParamBuilder withParam(String name, TextEnum value) {
        return withParam(name, value == null ? null : value.getTextId());
    }

    /**
     * Add parameter to sql map and check that the text enum is not null, if not get
     * the text id.
     * 
     * @param name  The name of the parameter.
     * @param value The value of the parameter
     * @return this builder object {@link SqlParamBuilder}
     */
    public SqlParamBuilder withParam(String name, Boolean value) {
        return withParam(name, value == null ? null : value ? 1 : 0);
    }

    /**
     * Add parameter to sql map for an enum collection and check that the text enum
     * is not null, if not get the text id.
     * 
     * @param name  The name of the parameter.
     * @param value The value of the parameter
     * @return this builder object {@link SqlParamBuilder}
     */
    public SqlParamBuilder withParamTextEnumCollection(String name, Collection<? extends TextEnum> values) {
        return withParam(name,
                values == null ? null : values.stream().map(TextEnum::getTextId).collect(Collectors.toList()));
    }

    /**
     * Adds search capabilities to the query. If it has an instance of a
     * {@link SearchParam} then it will enable search and add the search if the
     * value exists.
     * 
     * @return {@link SqlParamBuilder} with the search ability.
     */
    public SqlParamBuilder useSearch() {
        if (!(commonParam instanceof SearchParam)) {
            return this;
        }

        SearchParam searchParam = (SearchParam) commonParam;

        if (searchParam.getSearch() == null) {
            return this;
        }

        this.sqlParams.addValue("searchEnabled", true);
        this.sqlParams.addValue("searchValue", String.format("%%%s%%", searchParam.getSearch()));
        return this;
    }

    /**
     * These are what fields the query will search on. If search is enabled,
     * {@link SearchField} has to be on the object.
     * 
     * @return {@link SqlParamBuilder} with the search fields.
     */
    public SqlParamBuilder useSearchField() {
        if (!(commonParam instanceof SearchFieldParams)) {
            return this;
        }

        SearchFieldParams<? extends SearchField> searchFieldParams = (SearchFieldParams<?>) commonParam;

        String searchFieldSql = "";

        List<SearchField> fieldList = searchFieldParams.getSearchFields();
        for (int i = 0; i < fieldList.size(); i++) {
            searchFieldSql += String.format("%s LIKE :searchValue", fieldList.get(i).getColumn());
            searchFieldSql += i == fieldList.size() - 1 ? "" : " OR ";
        }

        this.sqlParams.addValue("searchContent", searchFieldSql.trim());
        return this;
    }

    /**
     * Will add all search field capabilities to the sql builder.
     * 
     * @return {@link SqlParamBuilder} object.
     */
    public SqlParamBuilder useAllParams() {
        useSearch();
        return useSearchField();
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
