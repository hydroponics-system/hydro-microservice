package com.hydro.app.growchamberhistory.client.domain.request;

import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * GrowChamberHistoryGetRequest for filtering grow chamber history logs.
 *
 * @author Sam Butler
 * @since May 26, 2022
 */
@Schema(description = "Grow Chamber History get request object for filtering logs.")
public class GrowChamberHistoryGetRequest {

    @Schema(description = "List of Grow Chamber ids.")
    private Set<Integer> id;

    @Schema(description = "List system ids.")
    private Set<Integer> systemId;

    @Schema(description = "The status of the lights.")
    private Boolean lightStatus;

    public Set<Integer> getId() {
        return id;
    }

    public void setId(Set<Integer> id) {
        this.id = id;
    }

    public Set<Integer> getSystemId() {
        return systemId;
    }

    public void setSystemId(Set<Integer> systemId) {
        this.systemId = systemId;
    }

    public Boolean getLightStatus() {
        return lightStatus;
    }

    public void setLightStatus(Boolean lightStatus) {
        this.lightStatus = lightStatus;
    }
}
