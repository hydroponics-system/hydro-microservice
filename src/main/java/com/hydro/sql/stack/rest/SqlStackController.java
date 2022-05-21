package com.hydro.sql.stack.rest;

import static org.springframework.http.HttpStatus.OK;

import com.hydro.annotations.interfaces.HasAccess;
import com.hydro.app.user.client.domain.enums.WebRole;
import com.hydro.sql.stack.domain.DatabaseStack;
import com.hydro.sql.stack.service.SqlStackService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@RequestMapping("/api/sql/stack")
@RestController
@Api(tags = { "Stack Controller" }, description = "Endpoints for managing a db stack instance")
public class SqlStackController {

    @Autowired
    private SqlStackService service;

    /**
     * Method for creating a stack for the given database user.
     * 
     * @param dbUsername The db user name.
     */
    @PostMapping("/{dbUsername}")
    @HasAccess(WebRole.DEVELOPER)
    public DatabaseStack createStack(@PathVariable String dbUsername) {
        return service.createStack(dbUsername);
    }

    /**
     * Method for granting access for a user to a stack.
     * 
     * @param stackName  The name of the stack to grant access too.
     * @param dbUsername The db user name.
     */
    @PostMapping("/{stackName}/user/{dbUsername}")
    @ResponseStatus(OK)
    @HasAccess(WebRole.ADMIN)
    public void grantAccessToUserOnStack(@PathVariable String stackName, @PathVariable String dbUsername) {
        service.grantAccessToUserOnStack(stackName, dbUsername);
    }

    /**
     * Method that will take in a stack name and database user that needs to be
     * revoked.
     * 
     * @param stackName  The name of the stack to revoke access too.
     * @param dbUsername The db user name.
     */
    @DeleteMapping("/{stackName}")
    @ResponseStatus(OK)
    @HasAccess(WebRole.ADMIN)
    public void dropStack(@PathVariable String stackName) {
        service.dropStack(stackName);
    }

    /**
     * Method that will take in a stack name and database user that needs to be
     * revoked.
     * 
     * @param stackName  The name of the stack to revoke access too.
     * @param dbUsername The db user name.
     */
    @DeleteMapping("/{stackName}/user/{dbUsername}")
    @ResponseStatus(OK)
    @HasAccess(WebRole.ADMIN)
    public void revokeAccessToUserOnStack(@PathVariable String stackName, @PathVariable String dbUsername) {
        service.revokeAccessToUserOnStack(stackName, dbUsername);
    }
}
