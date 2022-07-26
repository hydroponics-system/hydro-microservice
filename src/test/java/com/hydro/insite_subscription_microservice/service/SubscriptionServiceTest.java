package com.hydro.insite_subscription_microservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.hydro.factory.annotations.HydroServiceTest;
import com.hydro.insite_jwt_microservice.utility.JwtHolder;
import com.hydro.insite_subscription_microservice.client.domain.NotificationAction;
import com.hydro.insite_subscription_microservice.client.domain.NotificationEnvelope;
import com.hydro.insite_subscription_microservice.notification.UserNotification;

@HydroServiceTest
public class SubscriptionServiceTest {

    @Mock
    private WebNotifierService webNotifierService;

    @Mock
    private JwtHolder jwtHolder;

    @InjectMocks
    private SubscriptionService service;

    @Captor
    private ArgumentCaptor<NotificationEnvelope<?>> sendNotificationCaptor;

    @Test
    public void testPushWithJustBody() {
        when(jwtHolder.getUserId()).thenReturn(12);

        UserNotification userSub = new UserNotification();
        userSub.setName("Test User");
        userSub.setUserId(12);

        service.push(userSub);

        verify(jwtHolder).getUserId();
        verify(webNotifierService).sendNotification(sendNotificationCaptor.capture());

        NotificationEnvelope<?> envelope = sendNotificationCaptor.getValue();
        assertEquals(envelope.getBody().getClass(), UserNotification.class, "Should be UserSubscription class");

        UserNotification resultSub = (UserNotification) envelope.getBody();
        assertEquals(resultSub.getName(), "Test User", "User Name");
        assertEquals(resultSub.getUserId(), 12, "User Id");
        assertEquals(NotificationAction.CREATE, envelope.getAction(), "Notification Action");
        assertEquals("/topic/notification", envelope.getDestination(), "Notification Destination");
        assertEquals(12, envelope.getUserId(), "Notification User ID");
    }

    @Test
    public void testPushWithBodyAndNotificationAction() {
        when(jwtHolder.getUserId()).thenReturn(12);

        service.push(NotificationAction.DELETE, new UserNotification());

        verify(jwtHolder).getUserId();
        verify(webNotifierService).sendNotification(sendNotificationCaptor.capture());

        NotificationEnvelope<?> envelope = sendNotificationCaptor.getValue();
        assertEquals(envelope.getBody().getClass(), UserNotification.class, "Should be UserSubscription class");
        assertEquals(NotificationAction.DELETE, envelope.getAction(), "Notification Action");
        assertEquals("/topic/notification", envelope.getDestination(), "Notification Destination");
        assertEquals(12, envelope.getUserId(), "Notification User ID");
    }
}
