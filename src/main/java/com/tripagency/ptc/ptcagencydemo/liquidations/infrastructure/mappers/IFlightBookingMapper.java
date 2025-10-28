package com.tripagency.ptc.ptcagencydemo.liquidations.infrastructure.mappers;

import com.tripagency.ptc.ptcagencydemo.liquidations.domain.entities.DFlightBooking;
import com.tripagency.ptc.ptcagencydemo.liquidations.infrastructure.entities.FlightBooking;
import com.tripagency.ptc.ptcagencydemo.users.infrastructure.mappers.ICurrencyMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring", uses = {ILiquidationEnumMapper.class, ICurrencyMapper.class})
public interface IFlightBookingMapper {
    
    @Mapping(target = "flightService", ignore = true)
    @Mapping(source = "costamarBookingCode", target = "costamarBookingCode")
    @Mapping(target = "isActive", defaultValue = "true")
    FlightBooking toInfrastructure(DFlightBooking domain);
    
    @Mapping(source = "costamarBookingCode", target = "costamarBookingCode")
    DFlightBooking toDomain(FlightBooking infrastructure);
    
    List<FlightBooking> toInfrastructureList(List<DFlightBooking> domainList);
    List<DFlightBooking> toDomainList(List<FlightBooking> infrastructureList);
    
    default String mapOptionalToString(Optional<String> optional) {
        return optional != null ? optional.orElse(null) : null;
    }
    
    default Optional<String> mapStringToOptional(String value) {
        return Optional.ofNullable(value);
    }
}
