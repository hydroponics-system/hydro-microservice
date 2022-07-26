package com.hydro.insite_subscription_microservice.client.domain;

/**
 * Added to entities to allow for notification type to be standardized when
 * publishing a message.
 * 
 * @author Sam Butler
 * @since March 24, 2022
 */
public interface NotificationBody {

    /**
     * A text value identifying the notification body type.
     * 
     * @return NotificationBodyType
     */
    NotificationBodyType getBodyType();
}
