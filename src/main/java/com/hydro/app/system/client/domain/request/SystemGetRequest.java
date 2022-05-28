package com.hydro.app.system.client.domain.request;

import java.util.Set;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * System get request for filtering systems.
 *
 * @author Sam Butler
 * @since May 26, 2022
 */
@ApiModel(description = "System get request object for filtering systems.")
public class SystemGetRequest {

    @ApiModelProperty(value = "List System ids.")
    private Set<Integer> id;

    public Set<Integer> getId() {
        return id;
    }

    public void setId(Set<Integer> id) {
        this.id = id;
    }
}
