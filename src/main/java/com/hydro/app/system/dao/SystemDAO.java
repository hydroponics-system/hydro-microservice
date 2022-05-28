package com.hydro.app.system.dao;

import javax.sql.DataSource;

import com.hydro.app.system.client.domain.HydroSystem;
import com.hydro.common.abstracts.BaseDao;
import com.hydro.common.sql.SqlParamBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

/**
 * Class that handles all the dao calls to the database for grow chamber logs.
 * 
 * @author Sam Butler
 * @since May 25, 2021
 */
@Repository
public class SystemDAO extends BaseDao {

    @Autowired
    public SystemDAO(DataSource source) {
        super(source);
    }

    /**
     * Method for registering a new hydroponic system.
     * 
     * @param systemName The name of the system to be registered.
     * @return {@link Integer} of the unique identifer of the system.
     */
    public int registerSystem(HydroSystem sys) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = SqlParamBuilder.with().withParam(UUID, sys.getUuid())
                .withParam(PART_NUMBER, sys.getPartNumber().toString()).withParam(NAME, sys.getName()).build();

        post(getSql("registerSystem", params), params, keyHolder);
        return keyHolder.getKey().intValue();
    }

    /**
     * Gets the next auto increment id of a system that will be inserted.
     * 
     * @return {@link Long} of the next auto increment id of a system.
     */
    public long getNextSystemId() {
        return get(getSql("nextSystemId"), new MapSqlParameterSource(), Long.class);
    }
}
