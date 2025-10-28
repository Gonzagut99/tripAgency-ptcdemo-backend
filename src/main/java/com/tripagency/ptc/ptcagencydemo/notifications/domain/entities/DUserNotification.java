package com.tripagency.ptc.ptcagencydemo.notifications.domain.entities;

import com.tripagency.ptc.ptcagencydemo.general.entities.domainEntities.BaseAbstractDomainEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DUserNotification extends BaseAbstractDomainEntity {
    
    private Boolean read;
    private Long userId;
    private Long notificationId;
    private DNotification notification;

    public DUserNotification(Long userId, Long notificationId, DNotification notification) {
        this.read = false;
        this.userId = userId;
        this.notificationId = notificationId;
        this.notification = notification;
        validateUserNotification();
    }

    private void validateUserNotification() {
        if (userId == null) {
            throw new IllegalArgumentException("El ID de usuario no puede ser nulo");
        }
        if (notificationId == null) {
            throw new IllegalArgumentException("El ID de notificación no puede ser nulo");
        }
    }

    public void markAsRead() {
        this.read = true;
    }
}
