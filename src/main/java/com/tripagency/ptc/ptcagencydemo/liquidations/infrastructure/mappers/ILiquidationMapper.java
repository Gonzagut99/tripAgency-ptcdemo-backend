package com.tripagency.ptc.ptcagencydemo.liquidations.infrastructure.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.tripagency.ptc.ptcagencydemo.liquidations.domain.entities.DLiquidation;
import com.tripagency.ptc.ptcagencydemo.liquidations.infrastructure.entities.Liquidation;

@Mapper(componentModel = "spring", uses = {
        ILiquidationEnumMapper.class,
        IPaymentMapper.class,
        IFlightServiceMapper.class,
        IHotelServiceMapper.class,
        ITourServiceMapper.class,
        IAdditionalServicesMapper.class,
        IIncidencyMapper.class
})
public interface ILiquidationMapper {

    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "staffOnCharge", ignore = true)
    @Mapping(target = "isActive", defaultValue = "true")
    Liquidation toInfrastructure(DLiquidation domain);
    
    @org.mapstruct.AfterMapping
    default void setLiquidationRelationships(@org.mapstruct.MappingTarget Liquidation target) {
        // Set bidirectional relationships for all services
        if (target.getFlightServices() != null) {
            target.getFlightServices().forEach(service -> service.setLiquidation(target));
        }
        if (target.getHotelServices() != null) {
            target.getHotelServices().forEach(service -> service.setLiquidation(target));
        }
        if (target.getTourServices() != null) {
            target.getTourServices().forEach(service -> service.setLiquidation(target));
        }
        if (target.getAdditionalServices() != null) {
            target.getAdditionalServices().forEach(service -> service.setLiquidation(target));
        }
        if (target.getPayments() != null) {
            target.getPayments().forEach(payment -> payment.setLiquidation(target));
        }
        if (target.getIncidencies() != null) {
            target.getIncidencies().forEach(incidency -> incidency.setLiquidation(target));
        }
    }

    DLiquidation toDomain(Liquidation infrastructure);

    List<Liquidation> toInfrastructureList(List<DLiquidation> domainList);

    List<DLiquidation> toDomainList(List<Liquidation> infrastructureList);
}
