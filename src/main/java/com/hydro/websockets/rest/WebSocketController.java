package com.hydro.websockets.rest;

import com.hydro.annotations.interfaces.RestApiController;
import com.hydro.websockets.service.WebSocketService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;

/**
 * WebSocketController for notifications
 * 
 * @author Sam Butler
 * @since Dec 13, 2020
 */
@RestApiController
@RequestMapping("/api/web-socket/notifications")
@Api(tags = { "Websocket Controller" }, description = "Endpoints for websocket integration.")
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
