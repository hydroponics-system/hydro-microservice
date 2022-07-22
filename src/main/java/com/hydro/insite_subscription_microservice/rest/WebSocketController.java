package com.hydro.insite_subscription_microservice.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hydro.insite_common_microservice.annotations.interfaces.RestApiController;
import com.hydro.insite_subscription_microservice.service.WebSocketService;

import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * WebSocketController for notifications
 * 
 * @author Sam Butler
 * @since Dec 13, 2020
 */
@RestApiController
@RequestMapping("/api/subscription/notifications")
@Tag(name = "Websocket Controller", description = "Endpoints for websocket integration.")
public class WebSocketController {

    @Autowired
    private WebSocketService service;

    /**
     * This will send the data to the specified socket.
     * 
     * @param data The data to be sent.
     */
    @PostMapping
    public void sendSystemUpdate(@RequestBody Object data) {
        service.sendSystemUpdate(data);
    }
}
