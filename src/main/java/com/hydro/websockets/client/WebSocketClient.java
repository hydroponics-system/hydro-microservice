package com.hydro.websockets.client;

import com.hydro.annotations.interfaces.Client;
import com.hydro.websockets.rest.WebSocketController;
import com.hydro.websockets.service.WebSocketService;

import org.springframework.beans.factory.annotation.Autowired;

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