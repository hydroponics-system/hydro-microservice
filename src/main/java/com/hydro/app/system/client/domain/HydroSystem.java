package com.hydro.app.system.client.domain;

import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Hydroponic System object.
 * 
 * @author Sam Butler
 * @since May 26, 2022
 */
@ApiModel(description = "Hydroponic System object holding information about the system.")
public class HydroSystem {

    @ApiModelProperty(value = "System identifier.")
    private int id;

    @ApiModelProperty(value = "Unique Universal identifier for the system.")
    private String uuid;

    @ApiModelProperty(value = "System part number.")
    private PartNumber partNumber;

    @ApiModelProperty(value = "Nickname for the system.")
    private String name;

    @ApiModelProperty(value = "When the log was created.")
    private LocalDateTime insertDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public PartNumber getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(PartNumber partNumber) {
        this.partNumber = partNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(LocalDateTime insertDate) {
        this.insertDate = insertDate;
    }

}
