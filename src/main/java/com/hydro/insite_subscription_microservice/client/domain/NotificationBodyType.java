package com.hydro.insite_subscription_microservice.client.domain;

import com.hydro.insite_common_microservice.enums.TextEnum;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * NotificationBodyType contains all known {@link NotificationBody}
 * declerations.
 *
 * @author Sam Butler
 * @since March 24, 2022
 */
@Schema(description = "Notification Body Types")
public enum NotificationBodyType implements TextEnum {
    USER("USER"),
    SYSTEM_FAILURE("SYSTEM_FAILURE");

    private String textId;

    private NotificationBodyType(String textId) {
        this.textId = textId;
    }

    /**
     * Returns the text ID key for the NotificationBodyType.
     *
     * @return String
     */
    @Override
    public String getTextId() {
        return textId;
    }

    /**
     * Will get the environment object enum from the passed in text value. If the
     * enum is invalid it will return null
     * 
     * @param text The text to process.
     * @return {@link NotificationBodyType} Object
     */
    public static NotificationBodyType get(String text) {
        if(text == null) {
            return null;
        }

        for(NotificationBodyType w : NotificationBodyType.values())
            if(w.toString().equals(text.toUpperCase())) return w;
        return null;
    }
}
