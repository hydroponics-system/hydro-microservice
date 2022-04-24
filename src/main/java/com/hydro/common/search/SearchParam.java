package com.hydro.common.search;

/**
 * Search param for setting a value to search for.
 * 
 * @author Sam Butler
 * @since February 2, 2022
 */
public interface SearchParam extends CommonParam {
    String getSearch();

    void setSearch(String search);
}
