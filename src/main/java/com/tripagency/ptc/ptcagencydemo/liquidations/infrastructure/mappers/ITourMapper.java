package com.tripagency.ptc.ptcagencydemo.liquidations.infrastructure.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.tripagency.ptc.ptcagencydemo.liquidations.domain.entities.DTour;
import com.tripagency.ptc.ptcagencydemo.liquidations.infrastructure.entities.Tour;
import com.tripagency.ptc.ptcagencydemo.users.infrastructure.mappers.ICurrencyMapper;

@Mapper(componentModel = "spring", uses = {ILiquidationEnumMapper.class, ICurrencyMapper.class})
public interface ITourMapper {
    
    @Mapping(target = "tourService", ignore = true)
    @Mapping(target = "isActive", defaultValue = "true")
    Tour toInfrastructure(DTour domain);
    
    DTour toDomain(Tour infrastructure);
    
    List<Tour> toInfrastructureList(List<DTour> domainList);
    List<DTour> toDomainList(List<Tour> infrastructureList);
}
