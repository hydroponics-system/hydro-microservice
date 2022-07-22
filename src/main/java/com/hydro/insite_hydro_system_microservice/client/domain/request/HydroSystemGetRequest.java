package com.hydro.insite_hydro_system_microservice.client.domain.request;

import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * System get request for filtering systems.
 *
 * @author Sam Butler
 * @since May 26, 2022
 */
@Schema(description = "System get request object for filtering systems.")
public class HydroSystemGetRequest {

    @Schema(description = "List System ids.")
    private Set<Integer> id;

    @Schema(description = "List of system part numbers.")
    private Set<String> uuid;

    @Schema(description = "List of system part numbers.")
    private Set<String> partNumber;

    @Schema(description = "List of system names.")
    private Set<String> name;

    public Set<Integer> getId() {
        return id;
    }

    public void setId(Set<Integer> id) {
        this.id = id;
    }

    public Set<String> getUuid() {
        return uuid;
    }

    public void setUuid(Set<String> uuid) {
        this.uuid = uuid;
    }

    public Set<String> getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(Set<String> partNumber) {
        this.partNumber = partNumber;
    }

    public Set<String> getName() {
        return name;
    }

    public void setName(Set<String> name) {
        this.name = name;
    }
}
