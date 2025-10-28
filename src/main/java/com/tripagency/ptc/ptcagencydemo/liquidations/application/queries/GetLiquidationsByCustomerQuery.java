package com.tripagency.ptc.ptcagencydemo.liquidations.application.queries;

import com.tripagency.ptc.ptcagencydemo.liquidations.presentation.dto.PaginatedLiquidationRequestDto;

public record GetLiquidationsByCustomerQuery(Long customerId, PaginatedLiquidationRequestDto requestDto) {
}
