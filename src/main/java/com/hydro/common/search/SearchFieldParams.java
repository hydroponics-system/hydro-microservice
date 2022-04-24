package com.hydro.common.search;

import java.util.List;

public interface SearchFieldParams<T extends SearchField> {
    public List<SearchField> getSearchFields();
}
