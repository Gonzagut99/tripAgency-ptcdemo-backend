package com.tripagency.ptc.ptcagencydemo.liquidations.application.events;

import java.util.Optional;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.tripagency.ptc.ptcagencydemo.notifications.application.services.NotificationService;
import com.tripagency.ptc.ptcagencydemo.notifications.domain.enums.DNotificationScope;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class IncidencyAddedEventHandler {
    
    private final NotificationService notificationService;

    public IncidencyAddedEventHandler(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @EventListener
    public void handleIncidencyAdded(IncidencyAddedDomainEvent event) {
        log.info("Handling IncidencyAddedDomainEvent for liquidation: {}", event.liquidationId());
        
        // Enviar notificación a todos los usuarios excepto el que creó la incidencia
        notificationService.sendNotification(
                event.message(),
                DNotificationScope.OTHERS,
                Optional.of(event.liquidationId().toString()),
                event.staffId()
        );
        
        log.info("Notification sent for incidency added to liquidation: {}", event.liquidationId());
    }
}
