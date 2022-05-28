package com.hydro.app.system.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.hydro.annotations.interfaces.HasAccess;
import com.hydro.annotations.interfaces.RestApiController;
import com.hydro.app.system.client.domain.HydroSystem;
import com.hydro.app.system.service.SystemService;
import com.hydro.app.user.client.domain.enums.WebRole;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RequestMapping("/api/system-app/system")
@RestApiController
@Api(tags = { "System Controller" }, description = "Endpoints for managing hydroponic systems.")
public class SystemController {

    @Autowired
    private SystemService service;

    /**
     * Method for registering a new hydroponic system.
     * 
     * @param systemName The name of the system to be registered.
     * @return {@link HydroSystem} that was registered.
     */
    @ApiOperation(value = "Create a new entry for a system", notes = "Will register a new system under the given name.")
    @PostMapping(value = "/{systemName}/register", produces = APPLICATION_JSON_VALUE)
    @HasAccess(WebRole.USER)
    public HydroSystem registerSystem(@PathVariable String systemName) {
        return service.registerSystem(systemName);
    }
}
