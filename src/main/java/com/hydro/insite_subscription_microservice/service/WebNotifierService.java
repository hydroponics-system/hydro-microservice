package com.hydro.insite_subscription_microservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.hydro.insite_common_microservice.util.HydroLogger;
import com.hydro.insite_subscription_microservice.client.domain.NotificationBody;
import com.hydro.insite_subscription_microservice.client.domain.NotificationEnvelope;

/**
 * Web Notifier Service wraps the common elements of sending web notifications
 * into one call.
 * 
 * @author Sam Butler
 * @since March 24, 2022
 */
@Service
public class WebNotifierService {

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private HydroLogger LOGGER;

    /**
     * Send a Web Notification for a given subscription match with the User
     * Notification set.
     * 
     * @param envelope {@link NotificationEnvelope} to be sent.
     */
    public <T extends NotificationBody> void sendNotification(NotificationEnvelope<T> envelope) {
        LOGGER.info("Sending Web Notification to '{}' with type '{}'",envelope.getDestination(),
                    envelope.getBody().getBodyType());
        template.convertAndSend(envelope.getDestination(),envelope);
    }
}
