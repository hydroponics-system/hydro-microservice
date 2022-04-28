package com.hydro.websockets.stomp;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;

import com.hydro.websockets.client.domain.UserPrincipal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

/**
 * Custom handshake handler for assigning a unique id to a new connection.
 * 
 * @author Sam Butler
 * @since March 24, 2022
 */
public class ClientHandshakeHandler extends DefaultHandshakeHandler {
    private final Logger LOGGER = LoggerFactory.getLogger(ClientHandshakeHandler.class);

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler,
            Map<String, Object> attributes) {
        String randomId = UUID.randomUUID().toString();
        LOGGER.info("Client connected to socket with ID '{}'", randomId);
        return new UserPrincipal(randomId);
    }

}
