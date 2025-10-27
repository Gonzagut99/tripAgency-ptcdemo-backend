package com.tripagency.ptc.ptcagencydemo.liquidations.application.queries;

import com.tripagency.ptc.ptcagencydemo.liquidations.domain.enums.DLiquidationStatus;
import com.tripagency.ptc.ptcagencydemo.liquidations.presentation.dto.PaginatedLiquidationRequestDto;

public record GetLiquidationsByStatusQuery(DLiquidationStatus status, PaginatedLiquidationRequestDto requestDto) {
}
