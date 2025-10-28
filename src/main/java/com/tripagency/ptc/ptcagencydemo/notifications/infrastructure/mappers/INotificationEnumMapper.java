package com.tripagency.ptc.ptcagencydemo.notifications.infrastructure.mappers;

import org.mapstruct.Mapper;

import com.tripagency.ptc.ptcagencydemo.notifications.domain.enums.DNotificationScope;
import com.tripagency.ptc.ptcagencydemo.notifications.infrastructure.enums.NotificationScope;

@Mapper(componentModel = "spring")
public interface INotificationEnumMapper {
    
    NotificationScope toInfrastructure(DNotificationScope domain);
    
    DNotificationScope toDomain(NotificationScope infrastructure);
}
