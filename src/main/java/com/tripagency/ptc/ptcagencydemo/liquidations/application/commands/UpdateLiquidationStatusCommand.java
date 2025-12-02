package com.tripagency.ptc.ptcagencydemo.liquidations.application.commands;

import com.tripagency.ptc.ptcagencydemo.liquidations.domain.enums.DLiquidationStatus;

public record UpdateLiquidationStatusCommand(
    Long liquidationId,
    DLiquidationStatus targetStatus
) {}
