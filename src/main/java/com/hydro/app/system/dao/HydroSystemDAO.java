package com.hydro.app.system.dao;

import static com.hydro.app.system.mapper.HydroSystemMapper.HYDRO_SYSTEM_MAPPER;

import java.util.List;

import javax.sql.DataSource;

import com.hydro.app.system.client.domain.HydroSystem;
import com.hydro.app.system.client.domain.request.HydroSystemGetRequest;
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
public class HydroSystemDAO extends BaseDao {

    @Autowired
    public HydroSystemDAO(DataSource source) {
        super(source);
    }

    /**
     * Method for getting a list of systems based on the given request.
     * 
     * @param request The hydro get request
     * @return List of {@link HydroSystem} objects.
     */
    public List<HydroSystem> getSystems(HydroSystemGetRequest request) {
        var params = SqlParamBuilder.with().withParam(ID, request.getId()).withParam(UUID, request.getPartNumber())
                .withParam(PART_NUMBER, request.getPartNumber()).withParam(NAME, request.getName()).build();

        return getPage(getSql("getSystems", params), params, HYDRO_SYSTEM_MAPPER);
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
                .withParam(PART_NUMBER, sys.getPartNumber().toString()).withParam(NAME, sys.getName())
                .withParam(INSERT_USER_ID, sys.getInsertUserId()).build();

        post(getSql("registerSystem", params), params, keyHolder);
        return keyHolder.getKey().intValue();
    }

    /**
     * Unregister a system by the given uuid. This will confirm that the system
     * being deleted is either by the user that created it or it is of a user with a
     * role of type ADMIN.
     * 
     * @param uuid The System unique identifier.
     */
    public void unregisterSystem(int id) {
        var params = parameterSource(ID, id);
        delete(getSql("unregisterSystem", params), params);
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
