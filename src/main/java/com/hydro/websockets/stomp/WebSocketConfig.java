package com.hydro.websockets.stomp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * Websocket config for setting ws endpoints and defining the handshake handler
 * that should be used on new session connections.
 * 
 * @author Sam Butler
 * @since March 24, 2022
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    private final Logger LOGGER = LoggerFactory.getLogger(WebSocketConfig.class);
    private final long DEFAULT_HEARTBEAT = 20000;

    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler ts = new ThreadPoolTaskScheduler();
        ts.setPoolSize(10);
        ts.initialize();
        return ts;
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.setUserDestinationPrefix("/user").enableSimpleBroker("/topic").setTaskScheduler(taskScheduler())
                .setHeartbeatValue(new long[] { DEFAULT_HEARTBEAT, DEFAULT_HEARTBEAT });
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        LOGGER.info("Websocket connection opened on uri '/api/websocket'");
        registry.addEndpoint("/api/websocket").setHandshakeHandler(new ClientHandshakeHandler()).setAllowedOrigins("*");
    }
}
