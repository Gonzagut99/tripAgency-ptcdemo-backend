package com.tripagency.ptc.ptcagencydemo.liquidations.application.commands;

import com.tripagency.ptc.ptcagencydemo.liquidations.domain.enums.DPaymentStatus;

public record UpdatePaymentStatusCommand(
    Long liquidationId,
    DPaymentStatus targetStatus
) {}
