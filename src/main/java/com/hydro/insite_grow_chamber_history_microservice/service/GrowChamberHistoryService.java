package com.hydro.insite_grow_chamber_history_microservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Sets;
import com.hydro.insite_common_microservice.exceptions.NotFoundException;
import com.hydro.insite_grow_chamber_history_microservice.client.domain.GrowChamberLog;
import com.hydro.insite_grow_chamber_history_microservice.client.domain.request.GrowChamberHistoryGetRequest;
import com.hydro.insite_grow_chamber_history_microservice.dao.GrowChamberHistoryDAO;

/**
 * Grow Chamber History class that handles all service calls to the dao
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Transactional
@Service
public class GrowChamberHistoryService {
    @Autowired
    private GrowChamberHistoryDAO dao;

    /**
     * Get a list of grow chamber logs for the given request.
     * 
     * @param request The request to filter the logs on.
     * @return List of {@link GrowChamberLog} objects.
     */
    public List<GrowChamberLog> getGrowChamberLogs(GrowChamberHistoryGetRequest request) {
        return dao.getGrowChamberLogs(request);
    }

    /**
     * Get a list of grow chamber logs by system id.
     * 
     * @param systemId The id of the system to get logs for.
     * @return List of {@link GrowChamberLog} objects.
     */
    public List<GrowChamberLog> getGrowChamberLogsBySystemId(int systemId) {
        GrowChamberHistoryGetRequest request = new GrowChamberHistoryGetRequest();
        request.setSystemId(Sets.newHashSet(systemId));
        return getGrowChamberLogs(request);
    }

    /**
     * Get a {@link GrowChamberLog} by log id.
     * 
     * @param id The id of the log
     * @return {@link GrowChamberLog} objects.
     */
    public GrowChamberLog getGrowChamberLogsById(int id) {
        GrowChamberHistoryGetRequest request = new GrowChamberHistoryGetRequest();
        request.setId(Sets.newHashSet(id));
        return getGrowChamberLogs(request).stream().findFirst()
                .orElseThrow(() -> new NotFoundException("Grow Chamber Log", id));
    }

    /**
     * Method for creating a new entry into the grow chamber history table.
     * 
     * @param log The {@link GrowChamberLog} object to be inserted.
     * @return {@link GrowChamberLog} of the log that was created.
     */
    public GrowChamberLog insertGrowChamberLog(GrowChamberLog log) {
        int growChamberId = dao.insertGrowChamberLog(log);
        return getGrowChamberLogsById(growChamberId);
    }
}
