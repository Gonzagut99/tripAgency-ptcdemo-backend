package com.tripagency.ptc.ptcagencydemo.liquidations.infrastructure.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.tripagency.ptc.ptcagencydemo.customers.infrastructure.mappers.ICustomerLombokMapper;
import com.tripagency.ptc.ptcagencydemo.liquidations.domain.entities.DLiquidation;
import com.tripagency.ptc.ptcagencydemo.liquidations.presentation.dto.LiquidationWithDetailsDto;
import com.tripagency.ptc.ptcagencydemo.users.infrastructure.mappers.IStaffLombokMapper;

@Mapper(componentModel = "spring", uses = {
        ILiquidationEnumMapper.class,
        IPaymentMapper.class,
        IFlightServiceMapper.class,
        IHotelServiceMapper.class,
        ITourServiceMapper.class,
        IAdditionalServicesMapper.class,
        IIncidencyMapper.class,
        ICustomerLombokMapper.class,
        IStaffLombokMapper.class
})
public interface ILiquidationWithDetailsMapper {
    
    @Mapping(target = "customer", expression = "java(domain.getCustomer().orElse(null))")
    @Mapping(target = "staffOnCharge", expression = "java(domain.getStaffOnCharge().orElse(null))")
    @Mapping(target = "createdAt", source = "createdDate")
    @Mapping(target = "updatedAt", source = "updatedDate")
    LiquidationWithDetailsDto toDto(DLiquidation domain);
}
