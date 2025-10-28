package com.tripagency.ptc.ptcagencydemo.liquidations.infrastructure.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.tripagency.ptc.ptcagencydemo.liquidations.domain.entities.DFlightService;
import com.tripagency.ptc.ptcagencydemo.liquidations.infrastructure.entities.FlightService;
import com.tripagency.ptc.ptcagencydemo.users.infrastructure.mappers.ICurrencyMapper;

@Mapper(componentModel = "spring", uses = {IFlightBookingMapper.class, ICurrencyMapper.class})
public interface IFlightServiceMapper {
    
    @Mapping(target = "liquidation", ignore = true)
    @Mapping(target = "isActive", defaultValue = "true")
    FlightService toInfrastructure(DFlightService domain);
    
    @org.mapstruct.AfterMapping
    default void setRelationships(@org.mapstruct.MappingTarget FlightService target) {
        if (target.getFlightBookings() != null) {
            target.getFlightBookings().forEach(booking -> booking.setFlightService(target));
        }
    }
    
    DFlightService toDomain(FlightService infrastructure);
    
    List<FlightService> toInfrastructureList(List<DFlightService> domainList);
    List<DFlightService> toDomainList(List<FlightService> infrastructureList);
}
