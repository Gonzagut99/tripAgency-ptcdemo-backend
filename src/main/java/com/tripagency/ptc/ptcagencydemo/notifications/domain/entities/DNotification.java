package com.tripagency.ptc.ptcagencydemo.notifications.domain.entities;

import java.util.Optional;

import com.tripagency.ptc.ptcagencydemo.general.entities.domainEntities.BaseAbstractDomainEntity;
import com.tripagency.ptc.ptcagencydemo.notifications.domain.enums.DNotificationScope;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DNotification extends BaseAbstractDomainEntity {
    
    private String message;
    private DNotificationScope scope;
    private Optional<String> referenceId;

    public DNotification(String message, DNotificationScope scope, Optional<String> referenceId) {
        this.message = message;
        this.scope = scope;
        this.referenceId = referenceId;
        validateNotification();
    }

    private void validateNotification() {
        if (message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("El mensaje de la notificación no puede ser nulo o vacío");
        }
        if (scope == null) {
            throw new IllegalArgumentException("El alcance de la notificación no puede ser nulo");
        }
    }
}
