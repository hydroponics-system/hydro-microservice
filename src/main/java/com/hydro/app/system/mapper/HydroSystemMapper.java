package com.hydro.app.system.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hydro.app.growchamberhistory.client.domain.GrowChamberLog;
import com.hydro.app.system.client.domain.HydroSystem;
import com.hydro.app.system.client.domain.PartNumber;
import com.hydro.common.abstracts.AbstractMapper;

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
		sys.setInsertDate(rs.getTimestamp(INSERT_DATE).toLocalDateTime());

		return sys;
	}
}
