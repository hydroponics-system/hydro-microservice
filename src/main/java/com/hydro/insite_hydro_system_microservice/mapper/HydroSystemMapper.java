package com.hydro.insite_hydro_system_microservice.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hydro.insite_common_microservice.abstracts.AbstractMapper;
import com.hydro.insite_grow_chamber_history_microservice.client.domain.GrowChamberLog;
import com.hydro.insite_hydro_system_microservice.client.domain.HydroSystem;
import com.hydro.insite_hydro_system_microservice.client.domain.PartNumber;

/**
 * Mapper class to map a Grow Chamber Log Object {@link GrowChamberLog}
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
public class HydroSystemMapper extends AbstractMapper<HydroSystem> {
	public static HydroSystemMapper HYDRO_SYSTEM_MAPPER = new HydroSystemMapper();

	public HydroSystem mapRow(ResultSet rs, int rowNum) throws SQLException {
		HydroSystem sys = new HydroSystem();

		sys.setId(rs.getInt(ID));
		sys.setUuid(rs.getString(UUID));
		sys.setPartNumber(new PartNumber(rs.getString(PART_NUMBER)));
		sys.setName(rs.getString(NAME));
		sys.setInsertUserId(rs.getInt(INSERT_USER_ID));
		sys.setInsertDate(rs.getTimestamp(INSERT_DATE).toLocalDateTime());

		return sys;
	}
}
