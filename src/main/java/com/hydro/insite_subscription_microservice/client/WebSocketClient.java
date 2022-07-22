package com.hydro.insite_subscription_microservice.client;

import org.springframework.beans.factory.annotation.Autowired;

import com.hydro.insite_common_microservice.annotations.interfaces.Client;
import com.hydro.insite_subscription_microservice.rest.WebSocketController;
import com.hydro.insite_subscription_microservice.service.WebSocketService;

/**
 * Client for {@link WebSocketService} to expose the given endpoint's to other
 * services.
 * 
 * @author Sam Butler
 * @since Dec 14, 2020
 */
@Client
public class WebSocketClient {

    @Autowired
    private WebSocketController controller;

    /**
     * This will send the data to the specified socket.
     * 
     * @param data The data to be sent.
     */
    public void sendSystemUpdate(Object data) throws Exception {
        controller.sendSystemUpdate(data);
    }
}