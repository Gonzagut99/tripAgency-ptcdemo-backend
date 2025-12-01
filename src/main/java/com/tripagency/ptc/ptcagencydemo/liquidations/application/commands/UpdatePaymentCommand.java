package com.tripagency.ptc.ptcagencydemo.liquidations.application.commands;

import com.tripagency.ptc.ptcagencydemo.liquidations.presentation.dto.UpdatePaymentDto;

public record UpdatePaymentCommand(
    Long liquidationId,
    Long paymentId,
    UpdatePaymentDto paymentDto
) {}
