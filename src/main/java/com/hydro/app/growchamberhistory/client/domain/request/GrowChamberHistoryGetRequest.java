package com.hydro.app.growchamberhistory.client.domain.request;

import java.util.Set;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * GrowChamberHistoryGetRequest for filtering grow chamber history logs.
 *
 * @author Sam Butler
 * @since May 26, 2022
 */
@ApiModel(description = "Grow Chamber History get request object for filtering logs.")
public class GrowChamberHistoryGetRequest {

    @ApiModelProperty(value = "List of Grow Chamber ids.")
    private Set<Integer> id;

    @ApiModelProperty(value = "List system ids.")
    private Set<Integer> systemId;

    @ApiModelProperty(value = "The status of the lights.")
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
