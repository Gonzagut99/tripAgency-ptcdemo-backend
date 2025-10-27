package com.tripagency.ptc.ptcagencydemo.notifications.infrastructure.mappers;

import java.util.Optional;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.tripagency.ptc.ptcagencydemo.notifications.domain.entities.DNotification;
import com.tripagency.ptc.ptcagencydemo.notifications.infrastructure.entities.Notification;

@Mapper(componentModel = "spring", uses = {INotificationEnumMapper.class})
public interface INotificationMapper {
    
    @Mapping(target = "scope", source = "scope")
    @Mapping(target = "referenceId", expression = "java(mapOptionalToString(domain.getReferenceId()))")
    Notification toInfrastructure(DNotification domain);
    
    @Mapping(target = "scope", source = "scope")
    @Mapping(target = "referenceId", expression = "java(mapStringToOptional(infrastructure.getReferenceId()))")
    DNotification toDomain(Notification infrastructure);
    
    default String mapOptionalToString(Optional<String> optional) {
        return optional.orElse(null);
    }
    
    default Optional<String> mapStringToOptional(String value) {
        return Optional.ofNullable(value);
    }
}
