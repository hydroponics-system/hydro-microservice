package com.hydro.app.system.service;

import java.time.LocalDateTime;
import java.util.UUID;

import com.hydro.ActiveProfile;
import com.hydro.app.system.client.domain.HydroSystem;
import com.hydro.app.system.client.domain.PartNumber;
import com.hydro.app.system.dao.SystemDAO;
import com.hydro.common.enums.Environment;
import com.hydro.common.util.CommonUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Grow Chamber History class that handles all service calls to the dao
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Transactional
@Service
public class SystemService {

    @Autowired
    private SystemDAO dao;

    @Autowired
    private ActiveProfile activeProfile;

    /**
     * Method for registering a new hydroponic system.
     * 
     * @param systemName The name of the system to be registered.
     * @return {@link HydroSystem} that was registered.
     */
    public HydroSystem registerSystem(String systemName) {
        HydroSystem sys = buildSystem(dao.getNextSystemId(), systemName);
        int systemId = dao.registerSystem(sys);

        sys.setId(systemId);
        sys.setInsertDate(LocalDateTime.now());
        return sys;
    }

    /**
     * Builds a hydro system object for the given system id.
     * 
     * @param systemId The system id
     * @return {@link Systen} object.
     */
    private HydroSystem buildSystem(long systemId, String name) {
        HydroSystem sys = new HydroSystem();

        String env = activeProfile.getEnvironment().equals(Environment.PRODUCTION) ? "P" : "D";
        PartNumber partNumber = new PartNumber(
                String.format("%06d%s%06d", CommonUtil.generateRandomNumber(6), env, dao.getNextSystemId()));
        UUID systemUUID = UUID.nameUUIDFromBytes(partNumber.toString().getBytes());

        sys.setName(name);
        sys.setPartNumber(partNumber);
        sys.setUuid(systemUUID.toString());
        return sys;
    }
}
