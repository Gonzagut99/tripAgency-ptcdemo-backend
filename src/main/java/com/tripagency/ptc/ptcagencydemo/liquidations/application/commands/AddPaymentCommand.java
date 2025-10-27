package com.tripagency.ptc.ptcagencydemo.liquidations.application.commands;

import com.tripagency.ptc.ptcagencydemo.liquidations.domain.enums.DPaymentMethod;

public record AddPaymentCommand(
    Long liquidationId,
    DPaymentMethod paymentMethod,
    float amount
) {}
