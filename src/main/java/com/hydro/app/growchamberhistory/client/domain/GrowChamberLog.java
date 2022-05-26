package com.hydro.app.growchamberhistory.client.domain;

import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Grow chamber log object.
 * 
 * @author Sam Butler
 * @since May 26, 2022
 */
@ApiModel(description = "Grow Chamber Log object for holding information about the grow chamber history.")
public class GrowChamberLog {

    @ApiModelProperty(value = "Log identifier.")
    private int id;

    @ApiModelProperty(value = "System identifier.")
    private int systemId;

    @ApiModelProperty(value = "PH level of the system.")
    private float ph;

    @ApiModelProperty(value = "Total dissolved solids of the system.")
    private float tds;

    @ApiModelProperty(value = "Water temp of the system in celsius.")
    private float waterTemp;

    @ApiModelProperty(value = "Air temp of the system in celsius.")
    private float airTemp;

    @ApiModelProperty(value = "Humidity level of the system.")
    private float humidity;

    @ApiModelProperty(value = "The status if the lights are on or off.")
    private boolean lightsOn;

    @ApiModelProperty(value = "When the log was created.")
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
