package com.hydro.app.system.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hydro.app.growchamberhistory.client.domain.GrowChamberLog;
import com.hydro.app.system.client.domain.HydroSystem;
import com.hydro.common.abstracts.AbstractMapper;

/**
 * Mapper class to map a Grow Chamber Log Object {@link GrowChamberLog}
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
public class SystemMapper extends AbstractMapper<HydroSystem> {
	public static SystemMapper GROW_CHAMBER_LOG_MAPPER = new SystemMapper();

	public HydroSystem mapRow(ResultSet rs, int rowNum) throws SQLException {
		HydroSystem sys = new HydroSystem();

		return sys;
	}
}
