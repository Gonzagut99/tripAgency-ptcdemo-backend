package com.tripagency.ptc.ptcagencydemo.liquidations.application.queries.handlers;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tripagency.ptc.ptcagencydemo.liquidations.application.queries.GetLiquidationByIdQuery;
import com.tripagency.ptc.ptcagencydemo.liquidations.domain.entities.DLiquidation;
import com.tripagency.ptc.ptcagencydemo.liquidations.domain.repositories.ILiquidationRepository;
import com.tripagency.ptc.ptcagencydemo.liquidations.infrastructure.mappers.ILiquidationWithDetailsMapper;
import com.tripagency.ptc.ptcagencydemo.liquidations.presentation.dto.LiquidationWithDetailsDto;

@Service
public class GetLiquidationByIdQueryHandler {
    
    private final ILiquidationRepository liquidationRepository;
    private final ILiquidationWithDetailsMapper detailsMapper;

    public GetLiquidationByIdQueryHandler(
            ILiquidationRepository liquidationRepository,
            ILiquidationWithDetailsMapper detailsMapper) {
        this.liquidationRepository = liquidationRepository;
        this.detailsMapper = detailsMapper;
    }

    public Optional<LiquidationWithDetailsDto> execute(GetLiquidationByIdQuery query) {
        return liquidationRepository.findById(query.liquidationId())
                .map(detailsMapper::toDto);
    }
}
