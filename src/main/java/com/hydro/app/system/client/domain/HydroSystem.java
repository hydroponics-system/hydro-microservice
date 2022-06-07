package com.hydro.app.system.client.domain;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Hydroponic System object.
 * 
 * @author Sam Butler
 * @since May 26, 2022
 */
@Schema(description = "Hydroponic System object holding information about the system.")
public class HydroSystem {

    @Schema(description = "System identifier.")
    private int id;

    @Schema(description = "Unique Universal identifier for the system.")
    private String uuid;

    @Schema(description = "System part number.")
    private PartNumber partNumber;

    @Schema(description = "Nickname for the system.")
    private String name;

    @Schema(description = "When the log was created.")
    private int insertUserId;

    @Schema(description = "When the log was created.")
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

    public int getInsertUserId() {
        return insertUserId;
    }

    public void setInsertUserId(int insertUserId) {
        this.insertUserId = insertUserId;
    }

    public LocalDateTime getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(LocalDateTime insertDate) {
        this.insertDate = insertDate;
    }

}
