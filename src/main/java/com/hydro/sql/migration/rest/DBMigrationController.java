package com.hydro.sql.migration.rest;

import static org.springframework.http.HttpStatus.OK;

import com.hydro.annotations.interfaces.HasAccess;
import com.hydro.app.user.client.domain.enums.WebRole;
import com.hydro.sql.migration.service.DBMigrationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@RequestMapping("/api/sql/migration")
@RestController
@Api(tags = { "Database Migration Controller" }, description = "Endpoints for managing sql script migration")
public class DBMigrationController {

    @Autowired
    private DBMigrationService service;

    /**
     * This will migrate all sql scripts starting from the passed in version. Any
     * scripts that have a version greater than or equal to the passed in version
     * will get run on the active environment database.
     * 
     * @param version The version to run the scripts off of.
     */
    @PostMapping("/{version}")
    @ResponseStatus(OK)
    @HasAccess(WebRole.ADMIN)
    public void migrateSqlScripts(@PathVariable String version) throws Exception {
        service.migrateSqlScripts(version);
    }

    /**
     * This will run a sql script matching the passed in sql version. The format
     * will be <MAJOR>.<MINOR>.<FIX>.<BUILD>
     * 
     * @param sqlVersion The version of the sql script to run.
     */
    @PostMapping("/{sqlVersion}/script")
    @ResponseStatus(OK)
    @HasAccess(WebRole.ADMIN)
    public void migrateSingleScript(@PathVariable String sqlVersion) throws Exception {
        service.migrateSingleScript(sqlVersion);
    }
}
