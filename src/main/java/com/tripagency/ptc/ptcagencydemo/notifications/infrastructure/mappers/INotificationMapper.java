package com.tripagency.ptc.ptcagencydemo.notifications.infrastructure.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.tripagency.ptc.ptcagencydemo.notifications.domain.entities.DNotification;
import com.tripagency.ptc.ptcagencydemo.notifications.infrastructure.entities.Notification;

@Mapper(componentModel = "spring", uses = {INotificationEnumMapper.class})
public interface INotificationMapper {
    
    @Mapping(target = "title", source = "title")
    @Mapping(target = "message", source = "message")
    @Mapping(target = "type", source = "type")
    @Mapping(target = "scope", source = "scope")
    @Mapping(target = "referenceId", source = "referenceId")
    @Mapping(target = "referenceType", source = "referenceType")
    Notification toInfrastructure(DNotification domain);
    
    @Mapping(target = "title", source = "title")
    @Mapping(target = "message", source = "message")
    @Mapping(target = "type", source = "type")
    @Mapping(target = "scope", source = "scope")
    @Mapping(target = "referenceId", source = "referenceId")
    @Mapping(target = "referenceType", source = "referenceType")
    DNotification toDomain(Notification infrastructure);
}
