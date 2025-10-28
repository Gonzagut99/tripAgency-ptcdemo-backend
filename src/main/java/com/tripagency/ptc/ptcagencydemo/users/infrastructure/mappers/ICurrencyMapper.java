package com.tripagency.ptc.ptcagencydemo.users.infrastructure.mappers;

import com.tripagency.ptc.ptcagencydemo.users.domain.enums.DCurrency;
import com.tripagency.ptc.ptcagencydemo.users.infrastructure.enums.Currency;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ICurrencyMapper {
    
    Currency toInfrastructure(DCurrency domain);
    DCurrency toDomain(Currency infrastructure);
}
