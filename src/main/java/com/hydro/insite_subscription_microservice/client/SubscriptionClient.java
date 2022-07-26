package com.hydro.insite_subscription_microservice.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.socket.server.WebSocketService;

import com.hydro.insite_common_microservice.annotations.interfaces.Client;
import com.hydro.insite_subscription_microservice.client.domain.NotificationAction;
import com.hydro.insite_subscription_microservice.client.domain.NotificationBody;
import com.hydro.insite_subscription_microservice.service.SubscriptionService;

/**
 * Client for {@link WebSocketService} to expose the given endpoint's to other
 * services.
 * 
 * @author Sam Butler
 * @since Dec 14, 2020
 */
@Client
public class SubscriptionClient {

    @Autowired
    private SubscriptionService service;

    /**
     * Push a web notification. It will perform a
     * {@link NotificationAction#CREATE} with the passed in notification body.
     * 
     * @param action The action to perform.
     * @param body The body to be sent.
     */
    public void push(NotificationBody body) {
        service.push(body);
    }

    /**
     * Push a web notification. It will perform a notification action with the
     * passed in notification body.
     * 
     * @param action The action to perform.
     * @param body The body to be sent.
     */
    public void push(NotificationAction action, NotificationBody body) {
        service.push(action,body);
    }
}