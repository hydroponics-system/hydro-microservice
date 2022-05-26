package com.hydro.app.growchamberhistory.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hydro.app.growchamberhistory.client.domain.GrowChamberLog;
import com.hydro.common.abstracts.AbstractMapper;

/**
 * Mapper class to map a Grow Chamber Log Object {@link GrowChamberLog}
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
public class GrowChamberLogMapper extends AbstractMapper<GrowChamberLog> {
	public static GrowChamberLogMapper GROW_CHAMBER_LOG_MAPPER = new GrowChamberLogMapper();

	public GrowChamberLog mapRow(ResultSet rs, int rowNum) throws SQLException {
		GrowChamberLog log = new GrowChamberLog();

		log.setId(rs.getInt(ID));
		log.setSystemId(rs.getInt(SYSTEM_ID));
		log.setPh(rs.getFloat(PH));
		log.setTds(rs.getFloat(TDS));
		log.setWaterTemp(rs.getFloat(WATER_TEMP_CELSIUS));
		log.setAirTemp(rs.getFloat(AIR_TEMP_CELSIUS));
		log.setHumidity(rs.getFloat(HUMIDITY));
		log.setLightsOn(rs.getBoolean(LIGHT_STATUS));
		log.setInsertDate(rs.getTimestamp(INSERT_DATE).toLocalDateTime());

		return log;
	}
}
