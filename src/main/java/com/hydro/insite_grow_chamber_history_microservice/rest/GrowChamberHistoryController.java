package com.hydro.insite_grow_chamber_history_microservice.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hydro.insite_common_microservice.annotations.interfaces.HasAccess;
import com.hydro.insite_common_microservice.annotations.interfaces.RestApiController;
import com.hydro.insite_grow_chamber_history_microservice.client.domain.GrowChamberLog;
import com.hydro.insite_grow_chamber_history_microservice.client.domain.request.GrowChamberHistoryGetRequest;
import com.hydro.insite_grow_chamber_history_microservice.openapi.TagGrowChamberHistory;
import com.hydro.insite_grow_chamber_history_microservice.service.GrowChamberHistoryService;
import com.hydro.insite_user_microservice.client.domain.enums.WebRole;

import io.swagger.v3.oas.annotations.Operation;

@RequestMapping("/api/grow-chamber-history-app/logs")
@RestApiController
@TagGrowChamberHistory
public class GrowChamberHistoryController {

    @Autowired
    private GrowChamberHistoryService service;

    /**
     * Get a list of grow chamber logs for the given request.
     * 
     * @param request The request to filter the logs on.
     * @return List of {@link GrowChamberLog} objects.
     */
    @Operation(summary = "Get list of logs for the given request", description = "Given a GrowChamberHistoryGetRequest, it will get a list of logs that match the request")
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @HasAccess(WebRole.ADMIN)
    public List<GrowChamberLog> getGrowChamberLogs(GrowChamberHistoryGetRequest request) {
        return service.getGrowChamberLogs(request);
    }

    /**
     * Get a list of grow chamber logs by system id.
     * 
     * @param systemId The id of the system to get logs for.
     * @return List of {@link GrowChamberLog} objects.
     */
    @Operation(summary = "Get list of logs by system id", description = "Get a list of logs for a given system id to filter on.")
    @GetMapping(value = "/{systemId}/system", produces = APPLICATION_JSON_VALUE)
    @HasAccess(WebRole.ADMIN)
    public List<GrowChamberLog> getGrowChamberLogsBySystemId(@PathVariable int systemId) {
        return service.getGrowChamberLogsBySystemId(systemId);
    }

    /**
     * Get a {@link GrowChamberLog} by log id.
     * 
     * @param id The id of the log
     * @return {@link GrowChamberLog} objects.
     */
    @Operation(summary = "Get a grow chamber log by id", description = "Gets a single grow chamber log object by the id of the log.")
    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @HasAccess(WebRole.ADMIN)
    public GrowChamberLog getGrowChamberLogsById(@PathVariable int id) {
        return service.getGrowChamberLogsById(id);
    }

    /**
     * Method for creating a new entry into the grow chamber history table.
     * 
     * @param log The {@link GrowChamberLog} object to be inserted.
     * @return {@link GrowChamberLog} of the log that was created.
     */
    @Operation(summary = "Create a new entry in the grow chamber log", description = "Given a GrowChamberLog object, the entry will be inserted into the table.")
    @PostMapping(produces = APPLICATION_JSON_VALUE)
    @HasAccess(WebRole.SYSTEM_USER)
    public GrowChamberLog insertGrowChamberLog(@RequestBody GrowChamberLog log) {
        return service.insertGrowChamberLog(log);
    }
}
