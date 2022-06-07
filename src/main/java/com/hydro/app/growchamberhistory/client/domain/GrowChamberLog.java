package com.hydro.app.growchamberhistory.client.domain;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Grow chamber log object.
 * 
 * @author Sam Butler
 * @since May 26, 2022
 */
@Schema(description = "Grow Chamber Log object for holding information about the grow chamber history.")
public class GrowChamberLog {

    @Schema(description = "Log identifier.")
    private int id;

    @Schema(description = "System identifier.")
    private int systemId;

    @Schema(description = "PH level of the system.")
    private float ph;

    @Schema(description = "Total dissolved solids of the system.")
    private float tds;

    @Schema(description = "Water temp of the system in celsius.")
    private float waterTemp;

    @Schema(description = "Air temp of the system in celsius.")
    private float airTemp;

    @Schema(description = "Humidity level of the system.")
    private float humidity;

    @Schema(description = "The status if the lights are on or off.")
    private boolean lightsOn;

    @Schema(description = "When the log was created.")
    private LocalDateTime insertDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSystemId() {
        return systemId;
    }

    public void setSystemId(int systemId) {
        this.systemId = systemId;
    }

    public float getPh() {
        return ph;
    }

    public void setPh(float ph) {
        this.ph = ph;
    }

    public float getTds() {
        return tds;
    }

    public void setTds(float tds) {
        this.tds = tds;
    }

    public float getWaterTemp() {
        return waterTemp;
    }

    public void setWaterTemp(float waterTemp) {
        this.waterTemp = waterTemp;
    }

    public float getAirTemp() {
        return airTemp;
    }

    public void setAirTemp(float airTemp) {
        this.airTemp = airTemp;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public boolean isLightsOn() {
        return lightsOn;
    }

    public void setLightsOn(boolean lightsOn) {
        this.lightsOn = lightsOn;
    }

    public LocalDateTime getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(LocalDateTime insertDate) {
        this.insertDate = insertDate;
    }

}
