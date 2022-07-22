package com.hydro.insite_grow_chamber_history_microservice.dao;

import static com.hydro.insite_grow_chamber_history_microservice.mapper.GrowChamberLogMapper.GROW_CHAMBER_LOG_MAPPER;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.hydro.insite_common_microservice.abstracts.BaseDao;
import com.hydro.insite_grow_chamber_history_microservice.client.domain.GrowChamberLog;
import com.hydro.insite_grow_chamber_history_microservice.client.domain.request.GrowChamberHistoryGetRequest;
import com.hydro.insite_sql_microservice.SqlParamBuilder;

/**
 * Class that handles all the dao calls to the database for grow chamber logs.
 * 
 * @author Sam Butler
 * @since May 25, 2021
 */
@Repository
public class GrowChamberHistoryDAO extends BaseDao {

    @Autowired
    public GrowChamberHistoryDAO(DataSource source) {
        super(source);
    }

    /**
     * Get a list of grow chamber logs for the given request.
     * 
     * @param request The request to filter the logs on.
     * @return List of {@link GrowChamberLog} objects.
     */
    public List<GrowChamberLog> getGrowChamberLogs(GrowChamberHistoryGetRequest request) {
        MapSqlParameterSource params = SqlParamBuilder.with().withParam(ID, request.getId())
                .withParam(SYSTEM_ID, request.getSystemId()).withParam(LIGHT_STATUS, request.getLightStatus()).build();

        return getPage(getSql("getGrowChamberLogs", params), params, GROW_CHAMBER_LOG_MAPPER);
    }

    /**
     * Method for creating a new entry into the grow chamber history table.
     * 
     * @param log The {@link GrowChamberLog} object to be inserted.
     * @return {@link GrowChamberLog} of the log that was created.
     */
    public int insertGrowChamberLog(GrowChamberLog log) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = SqlParamBuilder.with().withParam(SYSTEM_ID, log.getSystemId())
                .withParam(PH, log.getPh()).withParam(TDS, log.getTds())
                .withParam(WATER_TEMP_CELSIUS, log.getWaterTemp()).withParam(AIR_TEMP_CELSIUS, log.getAirTemp())
                .withParam(HUMIDITY, log.getHumidity()).withParam(LIGHT_STATUS, log.isLightsOn()).build();

        post(getSql("insertGrowChamberLog", params), params, keyHolder);
        return keyHolder.getKey().intValue();
    }
}
