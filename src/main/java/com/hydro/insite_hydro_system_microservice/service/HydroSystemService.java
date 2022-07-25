package com.hydro.insite_hydro_system_microservice.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Sets;
import com.hydro.insite_common_microservice.enums.Environment;
import com.hydro.insite_common_microservice.exceptions.InsufficientPermissionsException;
import com.hydro.insite_common_microservice.exceptions.NotFoundException;
import com.hydro.insite_common_microservice.util.CommonUtil;
import com.hydro.insite_common_microservice.util.HydroLogger;
import com.hydro.insite_hydro_system_microservice.client.domain.HydroSystem;
import com.hydro.insite_hydro_system_microservice.client.domain.PartNumber;
import com.hydro.insite_hydro_system_microservice.client.domain.request.HydroSystemGetRequest;
import com.hydro.insite_hydro_system_microservice.dao.HydroSystemDAO;
import com.hydro.insite_jwt_microservice.utility.JwtEnvironment;
import com.hydro.insite_jwt_microservice.utility.JwtHolder;
import com.hydro.insite_user_microservice.client.domain.enums.WebRole;

/**
 * Grow Chamber History class that handles all service calls to the dao
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Transactional
@Service
public class HydroSystemService {

    @Autowired
    private HydroSystemDAO dao;

    @Autowired
    private JwtHolder jwtHolder;

    @Autowired
    private HydroLogger LOGGER;

    /**
     * Method for getting a list of systems based on the given request.
     * 
     * @param request The hydro get request
     * @return List of {@link HydroSystem} objects.
     */
    public List<HydroSystem> getSystems(HydroSystemGetRequest request) {
        List<HydroSystem> systems = dao.getSystems(request);
        LOGGER.info("System list response: '{}'", systems.size());
        return systems;
    }

    /**
     * Method for getting a system by system id.
     * 
     * @param id The systems unique identifier.
     * @return {@link HydroSystem} that matches the id
     */
    public HydroSystem getSystemById(int id) {
        HydroSystemGetRequest request = new HydroSystemGetRequest();
        request.setId(Sets.newHashSet(id));
        return getSystems(request).stream().findFirst().orElseThrow(() -> new NotFoundException("ID", id));
    }

    /**
     * Method for getting a system by uuid.
     * 
     * @param uuid The systems uuid.
     * @return {@link HydroSystem} that matches the uuid
     */
    public HydroSystem getSystemByUUID(String uuid) {
        HydroSystemGetRequest request = new HydroSystemGetRequest();
        request.setUuid(Sets.newHashSet(uuid));
        return getSystems(request).stream().findFirst().orElseThrow(() -> new NotFoundException("UUID", uuid));
    }

    /**
     * Method for registering a new hydroponic system.
     * 
     * @param systemName The name of the system to be registered.
     * @return {@link HydroSystem} that was registered.
     */
    public HydroSystem registerSystem(String systemName) {
        LOGGER.info("Registering new system with name: '{}'", systemName);
        HydroSystem sys = buildSystem(dao.getNextSystemId(), systemName);
        int systemId = dao.registerSystem(sys);

        sys.setId(systemId);
        sys.setInsertDate(LocalDateTime.now());
        LOGGER.info("New system registered successfully with UUID: '{}'", sys.getUUID());
        return sys;
    }

    /**
     * Unregister a system by the given id. This will confirm that the system being
     * deleted is either by the user that created it or it is of a user with a role
     * of type ADMIN.
     * 
     * @param id The System unique identifier.
     */
    public void unregisterSystem(int id) {
        HydroSystem sys = getSystemById(id);

        if (jwtHolder.getUserId() != sys.getInsertUserId() && !jwtHolder.getWebRole().equals(WebRole.ADMIN)) {
            throw new InsufficientPermissionsException("Insufficient permissions! You can not unregister this system.");
        }

        dao.unregisterSystem(id);
        LOGGER.info("System successfully unregistered with UUID: '{}'", sys.getUUID());
    }

    /**
     * Builds a hydro system object for the given system id.
     * 
     * @param systemId The system id
     * @return {@link Systen} object.
     */
    private HydroSystem buildSystem(long systemId, String name) {
        HydroSystem sys = new HydroSystem();

        String env = JwtEnvironment.getEnvironment().equals(Environment.PRODUCTION) ? "P" : "D";
        PartNumber partNumber = new PartNumber(
                String.format("%06d%s%06d", CommonUtil.generateRandomNumber(6), env, systemId));
        UUID systemUUID = UUID.nameUUIDFromBytes(partNumber.toString().getBytes());

        sys.setName(name);
        sys.setPartNumber(partNumber);
        sys.setUuid(systemUUID.toString());
        sys.setInsertUserId(jwtHolder.getUserId());
        return sys;
    }
}
