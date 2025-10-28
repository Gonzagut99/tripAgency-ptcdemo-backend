package com.tripagency.ptc.ptcagencydemo.notifications.infrastructure.entities;

import com.tripagency.ptc.ptcagencydemo.general.entities.repositoryEntites.BaseAbstractEntity;
import com.tripagency.ptc.ptcagencydemo.notifications.infrastructure.enums.NotificationScope;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
public class Notification extends BaseAbstractEntity {
    
    @Column(nullable = false, length = 500)
    private String message;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationScope scope;
    
    @Column(name = "reference_id")
    private String referenceId;
}
