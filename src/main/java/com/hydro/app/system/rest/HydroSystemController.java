package com.hydro.app.system.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.hydro.annotations.interfaces.HasAccess;
import com.hydro.annotations.interfaces.RestApiController;
import com.hydro.app.system.client.domain.HydroSystem;
import com.hydro.app.system.client.domain.request.HydroSystemGetRequest;
import com.hydro.app.system.service.HydroSystemService;
import com.hydro.app.user.client.domain.enums.WebRole;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RequestMapping("/api/system-app/system")
@RestApiController
@Tag(name = "System Controller", description = "Endpoints for managing hydroponic systems.")
public class HydroSystemController {

    @Autowired
    private HydroSystemService service;

    /**
     * Method for getting a list of systems based on the given request.
     * 
     * @param request The hydro get request
     * @return List of {@link HydroSystem} objects.
     */
    @Operation(summary = "Get a list of hydro systems.", description = "Will get a list of hydro systems based on the give HydroGetRequest.")
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @HasAccess(WebRole.USER)
    public List<HydroSystem> getSystems(HydroSystemGetRequest request) {
        return service.getSystems(request);
    }

    /**
     * Method for getting a system by id.
     * 
     * @param uuid The systems unique identifier.
     * @return {@link HydroSystem} that matches the id
     */
    @Operation(summary = "Get a system by unique identifier.", description = "Will get the system by the system id.")
    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @HasAccess(WebRole.USER)
    public HydroSystem getSystemById(@PathVariable int id) {
        return service.getSystemById(id);
    }

    /**
     * Method for getting a system by uuid.
     * 
     * @param uuid The systems uuid.
     * @return {@link HydroSystem} that matches the uuid
     */
    @Operation(summary = "Get a system by unique identifier.", description = "Will get the system by the uuid.")
    @GetMapping(value = "/{uuid}/uuid", produces = APPLICATION_JSON_VALUE)
    @HasAccess(WebRole.USER)
    public HydroSystem getSystemByUUID(@PathVariable String uuid) {
        return service.getSystemByUUID(uuid);
    }

    /**
     * Method for registering a new hydroponic system.
     * 
     * @param systemName The name of the system to be registered.
     * @return {@link HydroSystem} that was registered.
     */
    @Operation(summary = "Create a new entry for a system", description = "Will register a new system under the given name.")
    @PostMapping(value = "/{systemName}/register", produces = APPLICATION_JSON_VALUE)
    @HasAccess(WebRole.USER)
    public HydroSystem registerSystem(@PathVariable String systemName) {
        return service.registerSystem(systemName);
    }

    /**
     * Unregister a system by the given system id.
     * 
     * @param id The System unique identifier.
     */
    @Operation(summary = "Unregister a system.", description = "Takes in a system id and will unregister the system.")
    @DeleteMapping(value = "/{systemId}/unregister", produces = APPLICATION_JSON_VALUE)
    @HasAccess(WebRole.USER)
    @ResponseStatus(HttpStatus.OK)
    public void unregisterSystem(@PathVariable int systemId) {
        service.unregisterSystem(systemId);
    }
}
