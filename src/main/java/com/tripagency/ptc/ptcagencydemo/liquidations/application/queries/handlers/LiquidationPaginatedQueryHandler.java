package com.tripagency.ptc.ptcagencydemo.liquidations.application.queries.handlers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;

import com.tripagency.ptc.ptcagencydemo.liquidations.application.queries.LiquidationPaginatedQuery;
import com.tripagency.ptc.ptcagencydemo.liquidations.domain.entities.DLiquidation;
import com.tripagency.ptc.ptcagencydemo.liquidations.domain.repositories.ILiquidationRepository;

@Service
public class LiquidationPaginatedQueryHandler {

    private final ILiquidationRepository liquidationRepository;

    public LiquidationPaginatedQueryHandler(ILiquidationRepository liquidationRepository) {
        this.liquidationRepository = liquidationRepository;
    }

    public Page<DLiquidation> execute(LiquidationPaginatedQuery query) {
        Pageable pageable = PageRequest.of(
            query.requestDto().getPage(),
            query.requestDto().getSize(),
            Sort.by(Sort.Direction.DESC, "createdDate")
        );
        return liquidationRepository.findAll(pageable);
    }
}
