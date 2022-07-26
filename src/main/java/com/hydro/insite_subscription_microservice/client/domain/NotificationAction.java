package com.hydro.insite_subscription_microservice.client.domain;

import com.hydro.insite_common_microservice.enums.TextEnum;

/**
 * Notification Action types
 * 
 * @author Sam Butler
 * @since March 24, 2022
 */
public enum NotificationAction implements TextEnum {
    CREATE("C"),
    READ("R"),
    UPDATE("U"),
    DELETE("D");

    private String textId;

    private NotificationAction(String textId) {
        this.textId = textId;
    }

    /**
     * Returns the text ID key for Notification Action
     * 
     * @return String
     */
    @Override
    public String getTextId() {
        return textId;
    }

    /**
     * Will get the NotificationAction object enum from the passed in text
     * value. If the enum is invalid it will return null
     * 
     * @param text The text to process.
     * @return {@link NotificationAction} Object
     */
    public static NotificationAction get(String text) {
        if(text == null) {
            return null;
        }

        for(NotificationAction w : NotificationAction.values())
            if(w.toString().equals(text.toUpperCase())) return w;
        return null;
    }
}
