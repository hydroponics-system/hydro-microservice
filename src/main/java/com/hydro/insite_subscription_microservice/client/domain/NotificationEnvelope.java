package com.hydro.insite_subscription_microservice.client.domain;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Generic envelope that will be sent through the web notification process.
 * 
 * @author Sam Butler
 * @since March 24, 2022
 * @param <T> Implementation of {@link NotificationBody}
 */
@Schema(description = "Notification Envelope")
public class NotificationEnvelope<T extends NotificationBody> {

    @Schema(description = "The body to be sent with the notificaiton.")
    private T body;

    @Schema(description = "The user id the notification will be sent too.")
    private long userId;

    @Schema(description = "Where the notification should be sent too.")
    private String destination;

    @Schema(description = "Action type of the notification", allowableValues = {"C", "R", "U", "D"})
    private NotificationAction action;

    @Schema(description = "When the notification was created.")
    private LocalDateTime created;

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public NotificationAction getAction() {
        return action;
    }

    public void setAction(NotificationAction action) {
        this.action = action;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }
}
