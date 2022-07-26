package com.hydro.insite_subscription_microservice.stomp;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

import java.security.Principal;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.hydro.factory.annotations.HydroServiceTest;
import com.hydro.insite_common_microservice.util.HydroLogger;

@HydroServiceTest
public class SubscriptionHandshakeHandlerTest {

    @Mock
    private HydroLogger logger;

    @InjectMocks
    private SubscriptionHandshakeHandler handler;

    @Test
    public void testDetermineUser() {
        Principal u = handler.determineUser(null,null,null);

        assertNotNull(u.getName(),"Random UUID");
        verify(logger).info("Client connected to socket with ID '{}'",u.getName());
    }
}
