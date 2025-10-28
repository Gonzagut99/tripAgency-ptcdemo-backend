package com.tripagency.ptc.ptcagencydemo.liquidations.application.queries.handlers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tripagency.ptc.ptcagencydemo.liquidations.application.queries.GetLiquidationsByCustomerQuery;
import com.tripagency.ptc.ptcagencydemo.liquidations.domain.entities.DLiquidation;
import com.tripagency.ptc.ptcagencydemo.liquidations.domain.repositories.ILiquidationRepository;
import com.tripagency.ptc.ptcagencydemo.liquidations.infrastructure.mappers.ILiquidationWithDetailsMapper;
import com.tripagency.ptc.ptcagencydemo.liquidations.presentation.dto.LiquidationWithDetailsDto;

@Service
public class GetLiquidationsByCustomerQueryHandler {
    
    private final ILiquidationRepository liquidationRepository;
    private final ILiquidationWithDetailsMapper detailsMapper;

    public GetLiquidationsByCustomerQueryHandler(
            ILiquidationRepository liquidationRepository,
            ILiquidationWithDetailsMapper detailsMapper) {
        this.liquidationRepository = liquidationRepository;
        this.detailsMapper = detailsMapper;
    }

    public Page<LiquidationWithDetailsDto> execute(GetLiquidationsByCustomerQuery query) {
        Pageable pageable = PageRequest.of(
            query.requestDto().getPage(),
            query.requestDto().getSize()
        );
        Page<DLiquidation> liquidations = liquidationRepository.findByCustomerId(query.customerId(), pageable);
        return liquidations.map(detailsMapper::toDto);
    }
}
