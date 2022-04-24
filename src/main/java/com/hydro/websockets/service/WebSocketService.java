package com.hydro.websockets.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * Service method for building a notificaitons.
 * 
 * @author Sam Butler
 * @since March 23, 2022
 */
@Service
public class WebSocketService {

    @Autowired
    private SimpMessagingTemplate template;

    /**
     * This will send the data to the specified socket.
     * 
     * @param data The data to be sent.
     */
    public void sendSystemUpdate(Object data) {
        template.convertAndSend("/topic/update-system", data);
    }
}
