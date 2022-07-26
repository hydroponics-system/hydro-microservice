package com.hydro.insite_subscription_microservice.notification;

import com.hydro.insite_subscription_microservice.client.domain.NotificationBody;
import com.hydro.insite_subscription_microservice.client.domain.NotificationBodyType;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * User Subscription Notification
 * 
 * @author Sam Butler
 * @since March 24, 2022
 */
public class UserSubscription implements NotificationBody {
    @Schema(description = "The user id of the new user.")
    private int userId;

    @Schema(description = "The full name of the new user.")
    private String name;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public NotificationBodyType getBodyType() {
        return NotificationBodyType.USER;
    }
}