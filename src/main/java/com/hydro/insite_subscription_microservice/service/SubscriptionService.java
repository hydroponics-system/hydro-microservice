package com.hydro.insite_subscription_microservice.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hydro.insite_jwt_microservice.utility.JwtHolder;
import com.hydro.insite_subscription_microservice.client.domain.NotificationAction;
import com.hydro.insite_subscription_microservice.client.domain.NotificationBody;
import com.hydro.insite_subscription_microservice.client.domain.NotificationEnvelope;

/**
 * Subscription service for managing and processing notifications.
 * 
 * @author Sam Butler
 * @since July 26, 2022
 */
@Component
public class SubscriptionService {
    private static final String NOTIFICATION_DESTINATION = "/topic/notification";

    @Autowired
    private WebNotifierService webNotifierService;

    @Autowired
    private JwtHolder jwtHolder;

    /**
     * Push a web notification. It will perform a {@link NotificationAction#CREATE}
     * with the passed in notification body.
     * 
     * @param action The action to perform.
     * @param body   The body to be sent.
     */
    public void push(NotificationBody body) {
        push(NotificationAction.CREATE, body);
    }

    /**
     * Push a web notification to active user. It will perform a notification action
     * with the passed in notification body.
     * 
     * @param action The action to perform.
     * @param body   The body to be sent.
     */
    public void push(NotificationAction action, NotificationBody body) {
        push(action, body, jwtHolder.getUserId());
    }

    /**
     * Push a web notification. It will perform a notification action with the
     * passed in notification body.
     * 
     * @param action The action to perform.
     * @param body   The body to be sent.
     */
    public void push(NotificationAction action, NotificationBody body, int userId) {
        NotificationEnvelope<NotificationBody> envelope = new NotificationEnvelope<>();
        envelope.setAction(action);
        envelope.setBody(body);
        envelope.setDestination(NOTIFICATION_DESTINATION);
        envelope.setUserId(userId);
        envelope.setCreated(LocalDateTime.now());
        webNotifierService.sendNotification(envelope);
    }
}
